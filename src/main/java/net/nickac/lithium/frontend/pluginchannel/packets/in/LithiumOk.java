package net.nickac.lithium.frontend.pluginchannel.packets.in;

import net.nickac.lithium.backend.other.LithiumConstants;
import net.nickac.lithium.frontend.LithiumPlugin;
import net.nickac.lithium.frontend.players.LithiumPlayer;
import net.nickac.lithium.frontend.pluginchannel.packets.abstracts.PacketIn;

import java.util.List;

import static org.bukkit.ChatColor.*;

public class LithiumOk implements PacketIn {
    @Override
    public void execute(LithiumPlayer player, List<String> data) {
        String version = data.get(0);
        if (version.equalsIgnoreCase(LithiumConstants.VERSION)) {
            player.getHandle().sendMessage(GRAY + "[" + GOLD + "Lithium" + GRAY + "] " + GREEN + "Thank you for using Lithium!");
            LithiumPlugin.getInstance().getPlayerManager().setUsingLithium(player.getUniqueId(), true);
        } else {
            player.getHandle().sendMessage(GRAY + "[" + GOLD + "Lithium" + GRAY + "] " + RED + "Wrong version of Lithium! You need version: " + version);
        }

    }

    @Override
    public String key() {
        return LithiumConstants.TO_SERVER.CONNECTION_ESTABLISHED;
    }
}
