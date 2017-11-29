package net.nickac.lithium.frontend.pluginchannel.packets.out;

import net.nickac.lithium.backend.other.LithiumConstants;
import net.nickac.lithium.frontend.pluginchannel.packets.abstracts.PacketOut;

import java.util.Arrays;
import java.util.List;

public class CloseWindow implements PacketOut {
    @Override
    public List<String> execute() {
        return Arrays.asList(key());
    }

    @Override
    public String key() {
        return LithiumConstants.TO_CLIENT.CLOSE_WINDOW;
    }
}
