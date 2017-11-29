package net.nickac.lithium.frontend.pluginchannel.packets.in;

import net.nickac.lithium.backend.controls.LControl;
import net.nickac.lithium.backend.controls.impl.LSlider;
import net.nickac.lithium.backend.other.LithiumConstants;
import net.nickac.lithium.frontend.players.LithiumPlayer;
import net.nickac.lithium.frontend.pluginchannel.packets.abstracts.PacketIn;

import java.util.List;
import java.util.UUID;

public class SliderValueChanged implements PacketIn {


    @Override
    public void execute(LithiumPlayer player, List<String> data) {
        LControl w = player.getControlById(UUID.fromString(data.get(0)));
        if (w.getClass().equals(LSlider.class)) {
            LSlider sl = (LSlider) w;
            try {
                sl.setValue(Integer.parseInt(data.get(1)));
                sl.invokeValueChanged(player.getUniqueId());
            } catch (Exception e) {
            }
        }
    }

    @Override
    public String key() {
        return LithiumConstants.TO_SERVER.SLIDER_VALUE_CHANGED;
    }
}
