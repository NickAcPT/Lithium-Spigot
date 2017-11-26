package net.nickac.lithium.frontend.pluginchannel.packets.in;

import net.nickac.lithium.backend.controls.impl.LWindow;
import net.nickac.lithium.backend.other.LithiumConstants;
import net.nickac.lithium.frontend.players.LithiumPlayer;
import net.nickac.lithium.frontend.pluginchannel.packets.abstracts.PacketIn;

import java.util.List;
import java.util.UUID;

public class WindowClose implements PacketIn {
    @Override
    public void execute(LithiumPlayer player, List<String> data) {
        UUID windowID = UUID.fromString(data.get(0));
        LWindow w = (LWindow) player.geViewableById(windowID);
        w.invokeWindowClose(player.getUniqueId());
        player.removeViewable(windowID);
    }

    @Override
    public String key() {
        return LithiumConstants.TO_SERVER.WINDOW_CLOSE;
    }
}
