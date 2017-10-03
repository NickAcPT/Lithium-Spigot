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

package net.nickac.lithium.frontend.pluginchannel;

import net.nickac.lithium.backend.controls.LControl;
import net.nickac.lithium.backend.controls.impl.LButton;
import net.nickac.lithium.backend.controls.impl.LWindow;
import net.nickac.lithium.frontend.LithiumPlugin;
import net.nickac.lithium.frontend.LithiumUtils;
import net.nickac.lithium.frontend.players.LithiumPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.UUID;

import static net.nickac.lithium.backend.other.LithiumConstants.*;
import static net.nickac.lithium.frontend.LithiumPlugin.LITHIUM_CHANNEL;
import static org.bukkit.ChatColor.*;

/**
 * Created by NickAc for Lithium!
 */
public class LithiumListener implements PluginMessageListener {
	@Override
	public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
		if (!s.equals(LITHIUM_CHANNEL)) return;
		String msg = LithiumUtils.readUTF8String(bytes).trim();
		switch (msg) {
			case "Lithium|OK":
				player.sendMessage(GRAY + "[" + GOLD + "Lithium" + GRAY + "] " + GREEN + "Thank you for using Lithium!");
				LithiumPlugin.getInstance().getPlayerManager().setUsingLithium(player.getUniqueId(), true);
				break;
			default:
				String msg2 = msg.substring(0, msg.lastIndexOf('|') + 1);
				if (msg2.equals(LITHIUM_BUTTON_ACTION)) {
					LControl c = LithiumPlayer.getControlById(UUID.fromString(msg.substring(msg.lastIndexOf('|') + 1)));
					if (c != null && c.getClass().equals(LButton.class)) {
						LButton b = (LButton) c;
						b.invokeButtonClick(player.getUniqueId());
					}
				}
				if (msg2.equals(LITHIUM_WINDOW_OPEN)) {
					LWindow w = LithiumPlayer.getWindowById(UUID.fromString(msg.substring(msg.lastIndexOf('|') + 1)));
					if (w != null) w.invokeWindowLoad(player.getUniqueId());
				} else if (msg2.equals(LITHIUM_WINDOW_CLOSE)) {
					UUID windowID = UUID.fromString(msg.substring(msg.lastIndexOf('|') + 1));
					LWindow w = LithiumPlayer.getWindowById(windowID);
					w.invokeWindowClose(player.getUniqueId());
					LithiumPlayer.removeWindow(windowID);
				}
				break;
		}
	}
}