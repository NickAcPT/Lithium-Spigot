package net.nickac.lithium.frontend.pluginchannel.packets.in;

import net.nickac.lithium.backend.controls.IToggleable;
import net.nickac.lithium.backend.controls.LControl;
import net.nickac.lithium.backend.controls.impl.LCheckBox;
import net.nickac.lithium.backend.other.LithiumConstants;
import net.nickac.lithium.frontend.players.LithiumPlayer;
import net.nickac.lithium.frontend.pluginchannel.packets.abstracts.PacketIn;

import java.util.List;
import java.util.UUID;

public class ToggleAction implements PacketIn {
    @Override
    public void execute(LithiumPlayer player, List<String> data) {
        LControl w = player.getControlById(UUID.fromString(data.get(0)));
        if (w instanceof IToggleable) {
            IToggleable t = (IToggleable) w;
            t.setChecked(!t.isChecked());
            if (w.getClass().equals(LCheckBox.class)) {
                ((LCheckBox) w).invokeToggled(player.getUniqueId());
            }
        }
    }

    @Override
    public String key() {
        return LithiumConstants.TO_SERVER.TOGGLE_ACTION;
    }
}
