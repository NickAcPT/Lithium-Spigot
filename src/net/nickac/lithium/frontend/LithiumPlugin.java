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

package net.nickac.lithium.frontend;

import lombok.Getter;
import net.nickac.lithium.backend.other.LithiumConstants;
import net.nickac.lithium.frontend.events.PlayerEvents;
import net.nickac.lithium.frontend.players.LithiumPlayerManager;
import net.nickac.lithium.frontend.pluginchannel.LithiumListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by NickAc for Lithium!
 */
public class LithiumPlugin extends JavaPlugin {
	/**
	 * Get the plugin channel of Lithium
	 */
	public static String LITHIUM_CHANNEL = "Lithium";
	/**
	 * Instance
	 */
	private static LithiumPlugin instance;
	/**
	 * The player manager.<br>
	 * Used to open lithium windows.
	 */
	@Getter
	private LithiumPlayerManager playerManager;

	public static LithiumPlugin getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		instance = this;
		playerManager = new LithiumPlayerManager();
		LithiumConstants.onRefresh = (viewer, c) -> playerManager.getPlayer(Bukkit.getPlayer(viewer)).refreshControl(c.getUUID());
		LithiumConstants.onClose = (c, viewer) -> playerManager.getPlayer(Bukkit.getPlayer(viewer)).closeInterface();
		//We need to register the incoming message plugin message!
		Bukkit.getMessenger().registerIncomingPluginChannel(this, LITHIUM_CHANNEL, new LithiumListener());
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, LITHIUM_CHANNEL);
		Bukkit.getPluginManager().registerEvents(new PlayerEvents(), this);
	}

	@Override
	public void onDisable() {
		instance = null;
		LithiumConstants.onRefresh = null;
		LithiumConstants.onClose = null;
	}
}
