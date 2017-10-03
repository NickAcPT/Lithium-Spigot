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
	public static String LITHIUM_CHANNEL = "Lithium";
	private static LithiumPlugin instance;
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
