package net.nickac.lithium.frontend.pluginchannel.packets.out;

import net.nickac.lithium.backend.controls.LControl;
import net.nickac.lithium.backend.other.LithiumConstants;
import net.nickac.lithium.backend.serializer.SerializationUtils;
import net.nickac.lithium.frontend.pluginchannel.packets.abstracts.PacketOut;

import java.util.Arrays;
import java.util.List;

public class ControlChanged implements PacketOut {

    private LControl lControl;

    public ControlChanged(LControl lControl) {
        this.lControl = lControl;
    }

    @Override
    public List<String> execute() {
        return Arrays.asList(key(), SerializationUtils.objectToString(lControl));
    }

    @Override
    public String key() {
        return LithiumConstants.TO_CLIENT.CONTROL_CHANGED;
    }
}
