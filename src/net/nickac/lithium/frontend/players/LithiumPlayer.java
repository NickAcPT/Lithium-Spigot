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
		if (windows.containsKey(u)) windows.get(u).getControls().forEach(c -> controls.remove(c.getUUID()));
		windows.remove(u);
	}

	public void refreshControl(UUID uuid) {
		LControl c = getControlById(uuid);
		if (usingLithium && c != null) {
			handle.sendPluginMessage(
					LithiumPlugin.getInstance(),
					LithiumPlugin.LITHIUM_CHANNEL,
					LithiumUtils.writeUTF8String(LithiumConstants.LITHIUM_CONTROL_CHANGED + SerializationUtils.objectToString(c))
			);
		}
	}

	public void openInterface(LWindow w) {
		if (usingLithium) {
			if (w.getViewer() == null)
				w.setViewer(handle.getUniqueId());
			else
				return;
			windows.put(w.getUUID(), w);
			handle.sendPluginMessage(
					LithiumPlugin.getInstance(),
					LithiumPlugin.LITHIUM_CHANNEL,
					LithiumUtils.writeUTF8String(LithiumConstants.LITHIUM_RECEIVE_WINDOW + SerializationUtils.objectToString(w))
			);
			w.getControls().forEach(this::registerControl);
			setCurrentWindow(w);
			//TODO: Send stuff!
		}
	}

	private void registerControl(LControl c) {
		if (c == null) return;
		if (c instanceof LContainer)
			((LContainer) c).getControls().forEach(this::registerControl);
		controls.put(c.getUUID(), c);
	}

	public void closeInterface() {
		//TODO: Force Close!
		if (usingLithium) {
			handle.sendPluginMessage(
					LithiumPlugin.getInstance(),
					LithiumPlugin.LITHIUM_CHANNEL,
					LithiumUtils.writeUTF8String(LithiumConstants.LITHIUM_CLOSE_WINDOW)
			);
			setCurrentWindow(null);
		}
	}

}
