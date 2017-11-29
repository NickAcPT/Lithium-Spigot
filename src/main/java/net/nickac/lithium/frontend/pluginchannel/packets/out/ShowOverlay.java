package net.nickac.lithium.frontend.pluginchannel.packets.out;

import net.nickac.lithium.backend.controls.impl.LOverlay;
import net.nickac.lithium.backend.other.LithiumConstants;
import net.nickac.lithium.backend.serializer.SerializationUtils;
import net.nickac.lithium.frontend.pluginchannel.packets.abstracts.PacketOut;

import java.util.Arrays;
import java.util.List;

public class ShowOverlay implements PacketOut {

    private LOverlay overlay;

    public ShowOverlay(LOverlay overlay) {
        this.overlay = overlay;
    }

    @Override
    public List<String> execute() {
        return Arrays.asList(key(), SerializationUtils.objectToString(overlay));
    }

    @Override
    public String key() {
        return LithiumConstants.TO_CLIENT.SHOW_OVERLAY;
    }
}
