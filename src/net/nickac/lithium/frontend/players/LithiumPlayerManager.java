package net.nickac.lithium.frontend.players;

import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by NickAc for Lithium!
 */
public class LithiumPlayerManager {
	private HashMap<UUID, LithiumPlayer> players = new HashMap<>();

	public LithiumPlayerManager() {
	}

	public LithiumPlayer getPlayer(Player p) {
		return players.get(p.getUniqueId());
	}

	public void addPlayer(Player p) {
		players.put(p.getUniqueId(), new LithiumPlayer(p, false));
	}

	public boolean isUsingLithium(Player p) {
		return players.containsKey(p.getUniqueId()) && players.get(p.getUniqueId()).isUsingLithium();
	}

	public void setUsingLithium(UUID u, boolean using) {
		if (players.containsKey(u)) {
			players.get(u).setUsingLithium(using);
		}
	}

	public void removePlayer(@NonNull Player player) {
		players.remove(player.getUniqueId());
	}
}
