

/*
 * MIT License
 *
 * Copyright (c) 2017 NickAc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.nickac.lithium.frontend.pluginchannel;

import net.nickac.lithium.backend.controls.IToggleable;
import net.nickac.lithium.backend.controls.LControl;
import net.nickac.lithium.backend.controls.impl.*;
import net.nickac.lithium.backend.serializer.SerializationUtils;
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
		//player.sendMessage("§6[Lithium-Debug] " + msg);
		switch (msg) {
			case "Lithium|OK":
				player.sendMessage(GRAY + "[" + GOLD + "Lithium" + GRAY + "] " + GREEN + "Thank you for using Lithium!");
				LithiumPlugin.getInstance().getPlayerManager().setUsingLithium(player.getUniqueId(), true);
				break;
			default:
				int firstIndex = msg.indexOf('|') + 1;
				int secondIndex = msg.indexOf('|', firstIndex) + 1;
				int lastIndex = msg.lastIndexOf('|') + 1;
				String msg2 = msg.substring(0, secondIndex != -1 ? secondIndex : lastIndex);
				if (msg2.equals(LITHIUM_BUTTON_ACTION)) {
					LControl c = LithiumPlayer.getControlById(UUID.fromString(msg.substring(lastIndex)));
					if (c != null && c.getClass().equals(LButton.class)) {
						LButton b = (LButton) c;
						b.invokeButtonClick(player.getUniqueId());
					}
				}
				if (msg2.equals(LITHIUM_WINDOW_OPEN)) {
					LWindow w = LithiumPlayer.getWindowById(UUID.fromString(msg.substring(lastIndex)));
					if (w != null) w.invokeWindowLoad(player.getUniqueId());
				} else if (msg2.equals(LITHIUM_TEXTBOX_TEXT_CHANGED)) {
					LControl w = LithiumPlayer.getControlById(UUID.fromString(msg.substring(secondIndex, lastIndex - 1)));
					if (w != null && w.getClass().equals(LTextBox.class)) {
						LTextBox txt = (LTextBox) w;
						txt.setInternalText(SerializationUtils.stringToObject(msg.substring(lastIndex), String.class));
						//player.sendMessage("new: |" + msg.substring(lastIndex) + "|");
						txt.invokeTextChanged(player.getUniqueId());
					}
				} else if (msg2.equals(LITHIUM_WINDOW_CLOSE)) {
					UUID windowID = UUID.fromString(msg.substring(lastIndex));
					LWindow w = LithiumPlayer.getWindowById(windowID);
					w.invokeWindowClose(player.getUniqueId());
					LithiumPlayer.removeWindow(windowID);
				} else if (msg2.equals(LITHIUM_TOGGLE_ACTION)) {
					LControl w = LithiumPlayer.getControlById(UUID.fromString(msg.substring(lastIndex)));
					if (w instanceof IToggleable) {
						IToggleable t = (IToggleable) w;
						t.setChecked(!t.isChecked());
						if (w.getClass().equals(LCheckBox.class)) {
							((LCheckBox) w).invokeToggled(player.getUniqueId());
						}
					}
				} else if (msg2.equals(LITHIUM_SLIDER_VALUE_CHANGED)) {
					LControl w = LithiumPlayer.getControlById(UUID.fromString(msg.substring(secondIndex, lastIndex - 1)));
					if (w.getClass().equals(LSlider.class)) {
						LSlider sl = (LSlider) w;
						try {
							sl.setValue(Integer.parseInt(msg.substring(lastIndex)));
							sl.invokeValueChanged(player.getUniqueId());
						} catch (Exception e) {
							//Malformed packet!
							return;
						}
					}
				}
				break;
		}
	}
}
