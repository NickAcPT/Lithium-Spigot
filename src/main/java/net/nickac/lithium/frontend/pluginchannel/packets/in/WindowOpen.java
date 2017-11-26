package net.nickac.lithium.frontend.pluginchannel.packets.in;

import net.nickac.lithium.backend.controls.impl.LWindow;
import net.nickac.lithium.backend.other.LithiumConstants;
import net.nickac.lithium.frontend.players.LithiumPlayer;
import net.nickac.lithium.frontend.pluginchannel.packets.abstracts.PacketIn;

import java.util.List;
import java.util.UUID;

public class WindowOpen implements PacketIn {
    @Override
    public void execute(LithiumPlayer player, List<String> data) {
        LWindow w = (LWindow) player.geViewableById(UUID.fromString(data.get(0)));
        if (w != null) w.invokeWindowLoad(player.getUniqueId());
    }

    @Override
    public String key() {
        return LithiumConstants.TO_SERVER.WINDOW_OPEN;
    }
}
