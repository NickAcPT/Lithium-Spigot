package net.nickac.lithium.frontend.pluginchannel.packets.in;

import net.nickac.lithium.backend.controls.LControl;
import net.nickac.lithium.backend.controls.impl.LButton;
import net.nickac.lithium.backend.other.LithiumConstants;
import net.nickac.lithium.frontend.players.LithiumPlayer;
import net.nickac.lithium.frontend.pluginchannel.packets.abstracts.PacketIn;

import java.util.List;
import java.util.UUID;

public class ButtonAction implements PacketIn {
    @Override
    public void execute(LithiumPlayer player, List<String> data) {

        LControl c = player.getControlById(UUID.fromString(data.get(data.size()-1)));
        if (c != null && c.getClass().equals(LButton.class)) {
            LButton b = (LButton) c;
            b.invokeButtonClick(player.getUniqueId());
        }

    }

    @Override
    public String key() {
        return LithiumConstants.TO_SERVER.BUTTON_ACTION;
    }
}
