package net.nickac.lithium.frontend.container;

import net.nickac.lithium.backend.controls.LContainer;
import net.nickac.lithium.backend.controls.LContainerViewable;
import net.nickac.lithium.backend.controls.LControl;
import net.nickac.lithium.backend.controls.impl.LOverlay;
import net.nickac.lithium.backend.controls.impl.LWindow;
import net.nickac.lithium.backend.other.LithiumConstants;
import net.nickac.lithium.backend.serializer.SerializationUtils;
import net.nickac.lithium.frontend.players.LithiumPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ContainerManager {

    private Map<UUID,LContainerViewable> viewableMap = new HashMap<>();
    private HashMap<UUID, LControl> controls = new HashMap<>();

    static {
        //todo should be moved outside
        ContainerMap.instance.registerContainer(LWindow.class, LithiumConstants.TO_CLIENT.RECEIVE_WINDOW);
        ContainerMap.instance.registerContainer(LOverlay.class, LithiumConstants.TO_CLIENT.SHOW_OVERLAY);
    }

    public void openContainer(LithiumPlayer lithiumPlayer,LContainerViewable containerViewable){
        if(lithiumPlayer.isUsingLithium()){
            if(containerViewable.getViewer()==null){
                containerViewable.setViewer(lithiumPlayer.getHandle().getUniqueId());
            }else {
                return;
            }

            viewableMap.put(containerViewable.getUUID(),containerViewable);
            containerViewable.getControls().forEach(this::registerControl);
            lithiumPlayer.setCurrentContainer(containerViewable);
            lithiumPlayer.sendLithiumMessage(ContainerMap.instance.getByClass(containerViewable.getClass())+"|"+ SerializationUtils.objectToString(containerViewable));
        }
    }

    private void registerControl(LControl c) {
        if (c == null) return;
        if (c instanceof LContainer)
            ((LContainer) c).getControls().forEach(this::registerControl);
        controls.put(c.getUUID(), c);
    }

    public void removeControl(LControl l) {
        removeControl(l.getUUID());
    }

    public void removeControl(UUID l) {
        if (!controls.containsKey(l)) return;
        LControl ctrl = controls.get(l);
        if (ctrl instanceof LContainer) ((LContainer) ctrl).getControls().forEach(this::removeControl);
        controls.remove(l);
    }

    public void removeContainer(UUID u) {
        if (!viewableMap.containsKey(u))
            return;
        viewableMap.get(u).getControls().forEach(this::removeControl);
        viewableMap.remove(u);
    }

    public LContainerViewable getViewableById(UUID uuid){
        return viewableMap.get(uuid);
    }

    public LControl getControlById(UUID uuid){
        return controls.get(uuid);
    }

}
