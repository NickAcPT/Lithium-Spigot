/*
 * This file is part of Lithium.
 *
 * Lithium is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Lithium is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Lithium.  If not, see <http://www.gnu.org/licenses/>.
 */

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
