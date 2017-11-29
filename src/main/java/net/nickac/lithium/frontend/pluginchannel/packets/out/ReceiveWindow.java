package net.nickac.lithium.frontend.pluginchannel.packets.out;

import net.nickac.lithium.backend.controls.impl.LWindow;
import net.nickac.lithium.backend.other.LithiumConstants;
import net.nickac.lithium.backend.serializer.SerializationUtils;
import net.nickac.lithium.frontend.pluginchannel.packets.abstracts.PacketOut;

import java.util.Arrays;
import java.util.List;

public class ReceiveWindow implements PacketOut {

    private LWindow window;

    public ReceiveWindow(LWindow window) {
        this.window = window;
    }

    @Override
    public List<String> execute() {
        return Arrays.asList(key(), SerializationUtils.objectToString(window));
    }

    @Override
    public String key() {
        return LithiumConstants.TO_CLIENT.RECEIVE_WINDOW;
    }
}
