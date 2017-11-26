package net.nickac.lithium.frontend.pluginchannel.packets.in;

import net.nickac.lithium.backend.other.LithiumConstants;
import net.nickac.lithium.frontend.LithiumPlugin;
import net.nickac.lithium.frontend.players.LithiumPlayer;
import net.nickac.lithium.frontend.pluginchannel.packets.abstracts.PacketIn;

import java.util.List;

import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.GRAY;
import static org.bukkit.ChatColor.GREEN;

public class LithiumOk implements PacketIn {
    @Override
    public void execute(LithiumPlayer player, List<String> data) {
        player.getHandle().sendMessage(GRAY + "[" + GOLD + "Lithium" + GRAY + "] " + GREEN + "Thank you for using Lithium!");
        LithiumPlugin.getInstance().getPlayerManager().setUsingLithium(player.getUniqueId(), true);
    }

    @Override
    public String key() {
        return LithiumConstants.TO_SERVER.CONNECTION_ESTABLISHED;
    }
}
