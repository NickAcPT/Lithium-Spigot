package net.nickac.lithium.frontend.pluginchannel.packets.in;

import net.nickac.lithium.backend.controls.LControl;
import net.nickac.lithium.backend.controls.impl.LTextBox;
import net.nickac.lithium.backend.other.LithiumConstants;
import net.nickac.lithium.backend.serializer.SerializationUtils;
import net.nickac.lithium.frontend.players.LithiumPlayer;
import net.nickac.lithium.frontend.pluginchannel.packets.abstracts.PacketIn;

import java.util.List;
import java.util.UUID;

public class TextboxTextChanged implements PacketIn {
    @Override
    public void execute(LithiumPlayer player, List<String> data) {
        LControl w = player.getControlById(UUID.fromString(data.get(0)));
        if (w != null && w.getClass().equals(LTextBox.class)) {
            LTextBox txt = (LTextBox) w;
            txt.setInternalText(SerializationUtils.stringToObject(data.get(1), String.class));
            txt.invokeTextChanged(player.getUniqueId());
        }
    }

    @Override
    public String key() {
        return LithiumConstants.TO_SERVER.TEXTBOX_TEXT_CHANGED;
    }
}
