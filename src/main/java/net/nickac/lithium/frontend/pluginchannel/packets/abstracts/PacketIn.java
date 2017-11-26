package net.nickac.lithium.frontend.pluginchannel.packets.abstracts;

import net.nickac.lithium.frontend.players.LithiumPlayer;
import org.bukkit.entity.Player;

import java.util.List;

public interface PacketIn {

    void execute(LithiumPlayer player, List<String> data);

    String key();
}
