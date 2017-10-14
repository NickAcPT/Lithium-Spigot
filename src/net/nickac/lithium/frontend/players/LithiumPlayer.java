

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

package net.nickac.lithium.frontend.players;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.nickac.lithium.backend.controls.LContainer;
import net.nickac.lithium.backend.controls.LControl;
import net.nickac.lithium.backend.controls.impl.LWindow;
import net.nickac.lithium.backend.other.LithiumConstants;
import net.nickac.lithium.backend.serializer.SerializationUtils;
import net.nickac.lithium.frontend.LithiumPlugin;
import net.nickac.lithium.frontend.LithiumUtils;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by NickAc for Lithium!
 */
public class LithiumPlayer {

	private static HashMap<UUID, LWindow> windows = new HashMap<>();
	private static HashMap<UUID, LControl> controls = new HashMap<>();
	@Getter
	@Setter
	private LWindow currentWindow;
	@Getter
	private Player handle;
	@Getter
	@Setter(value = AccessLevel.PACKAGE)
	private boolean usingLithium;

	LithiumPlayer(Player handle, boolean usingLithium) {
		this.currentWindow = null;
		this.handle = handle;
		this.usingLithium = usingLithium;
	}

	public static LWindow getWindowById(UUID u) {
		return windows.getOrDefault(u, null);
	}

	public static LControl getControlById(UUID u) {
		return controls.getOrDefault(u, null);
	}

	public static void removeWindow(UUID u) {
		if (!windows.containsKey(u))
			return;
		windows.get(u).getControls().forEach(LithiumPlayer::removeControl);
		windows.remove(u);
	}

	public static void removeControl(LControl l) {
		removeControl(l.getUUID());
	}

	public static void removeControl(UUID l) {
		if (!controls.containsKey(l)) return;
		LControl ctrl = controls.get(l);
		if (ctrl instanceof LContainer) ((LContainer) ctrl).getControls().forEach(LithiumPlayer::removeControl);
		controls.remove(l);
	}

	public static void sendLithiumMessage(Player player, String msg) {
		player.sendPluginMessage(
				LithiumPlugin.getInstance(),
				LithiumPlugin.LITHIUM_CHANNEL,
				LithiumUtils.writeUTF8String(msg)
		);
	}

	public void refreshControl(UUID uuid) {
		LControl c = getControlById(uuid);
		if (usingLithium && c != null && uuid != null) {
			sendLithiumMessage(handle, LithiumConstants.LITHIUM_CONTROL_CHANGED + SerializationUtils.objectToString(c));
		}
	}

	private void registerControl(LControl c) {
		if (c == null) return;
		if (c instanceof LContainer)
			((LContainer) c).getControls().forEach(this::registerControl);
		controls.put(c.getUUID(), c);
	}

	public void openInterface(LWindow w) {
		if (usingLithium) {
			if (w.getViewer() == null)
				w.setViewer(handle.getUniqueId());
			else
				return;
			windows.put(w.getUUID(), w);
			sendLithiumMessage(handle, LithiumConstants.LITHIUM_RECEIVE_WINDOW + SerializationUtils.objectToString(w));
			w.getControls().forEach(this::registerControl);
			setCurrentWindow(w);
			//TODO: Send stuff!
		}
	}

	public void closeInterface() {
		//TODO: Force Close!
		if (usingLithium) {
			sendLithiumMessage(handle, LithiumConstants.LITHIUM_CLOSE_WINDOW);
			setCurrentWindow(null);
		}
	}

}
