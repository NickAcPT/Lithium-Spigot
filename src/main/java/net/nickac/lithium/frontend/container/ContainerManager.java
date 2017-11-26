package net.nickac.lithium.frontend.container;

import net.nickac.lithium.backend.controls.LContainer;
import net.nickac.lithium.backend.controls.LContainerViewable;
import net.nickac.lithium.backend.controls.LControl;
import net.nickac.lithium.frontend.players.LithiumPlayer;
import net.nickac.lithium.frontend.pluginchannel.packets.abstracts.PacketOut;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ContainerManager {

    private Map<UUID, LContainerViewable> viewableMap = new HashMap<>();
    private Map<UUID, LControl> controls = new HashMap<>();
    private ContainerMap containerMap;

    public ContainerManager(ContainerMap containerMap) {
        this.containerMap = containerMap;
    }

    public void openContainer(LithiumPlayer lithiumPlayer, LContainerViewable containerViewable) {
        if (lithiumPlayer.isUsingLithium()) {
            if (containerViewable.getViewer() == null) {
                containerViewable.setViewer(lithiumPlayer.getHandle().getUniqueId());
            } else {
                return;
            }

            Constructor<? extends PacketOut> packetConstructor = containerMap.getByClass(containerViewable.getClass());
            if (packetConstructor != null) {
                viewableMap.put(containerViewable.getUUID(), containerViewable);
                containerViewable.getControls().forEach(this::registerControl);
                lithiumPlayer.setCurrentContainer(containerViewable);

                try {
                    PacketOut packetObject = packetConstructor.newInstance(containerViewable);
                    System.out.println(packetObject.execute());
                    lithiumPlayer.sendLithiumMessage(String.join("|", packetObject.execute()));
                } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                    e.printStackTrace();
                }

            }
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

    public LContainerViewable getViewableById(UUID uuid) {
        return viewableMap.get(uuid);
    }

    public LControl getControlById(UUID uuid) {
        return controls.get(uuid);
    }

}
