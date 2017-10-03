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
		LithiumPlugin.getInstance().getPlayerManager().addPlayer(e.getPlayer());
	}

	@EventHandler
	public void on(PlayerQuitEvent e) {
		LithiumPlugin.getInstance().getPlayerManager().removePlayer(e.getPlayer());
	}
}
