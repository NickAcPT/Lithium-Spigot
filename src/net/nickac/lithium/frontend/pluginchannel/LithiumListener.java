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
