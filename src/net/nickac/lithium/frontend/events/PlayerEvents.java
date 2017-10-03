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

package net.nickac.lithium.frontend.events;

import net.nickac.lithium.frontend.LithiumPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by NickAc for Lithium!
 */
public class PlayerEvents implements Listener {


	@EventHandler
	public void on(PlayerJoinEvent e) {
		//We need to add the player to the manager, or else, developers would get null values.
		LithiumPlugin.getInstance().getPlayerManager().addPlayer(e.getPlayer());
	}

	@EventHandler
	public void on(PlayerQuitEvent e) {
		//The player has left! Just remove him!
		LithiumPlugin.getInstance().getPlayerManager().removePlayer(e.getPlayer());
	}
}
