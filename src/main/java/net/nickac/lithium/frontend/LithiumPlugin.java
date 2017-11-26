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

package net.nickac.lithium.frontend;

import lombok.Getter;
import net.nickac.lithium.backend.controls.LContainer;
import net.nickac.lithium.backend.controls.LControl;
import net.nickac.lithium.backend.other.LithiumConstants;
import net.nickac.lithium.backend.other.serverhandlers.LithiumRuntimeControlHandler;
import net.nickac.lithium.backend.serializer.SerializationUtils;
import net.nickac.lithium.frontend.container.ContainerManager;
import net.nickac.lithium.frontend.events.PlayerEvents;
import net.nickac.lithium.frontend.players.LithiumPlayer;
import net.nickac.lithium.frontend.players.LithiumPlayerManager;
import net.nickac.lithium.frontend.pluginchannel.LithiumListener;
import net.nickac.lithium.frontend.pluginchannel.packets.abstracts.PacketHandlerImpl;
import net.nickac.lithium.frontend.pluginchannel.packets.abstracts.PacketHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

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
    private PacketHandler lithiumPacketHandler;
    private ContainerManager containerManager;

    public static LithiumPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        this.containerManager = new ContainerManager();
        playerManager = new LithiumPlayerManager(containerManager);
        this.lithiumPacketHandler = new PacketHandlerImpl();
        LithiumConstants.onRefresh = (viewer, c) -> {
            Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
                if (viewer != null) {
                    LithiumPlayer lithiumPlayer = playerManager.getPlayer(Bukkit.getPlayer(viewer));
                    if (lithiumPlayer != null) {
                        lithiumPlayer.refreshControl(c.getUUID());
                    }
                }
            });

        };
        LithiumConstants.onControlRuntime = new LithiumRuntimeControlHandler() {
            @Override
            public void addControl(LControl c, LContainer ct, UUID viewer) {
                Player p = Bukkit.getPlayer(viewer);
                if (p != null && ct instanceof LControl) {
                    playerManager.getPlayer(p).sendLithiumMessage(LithiumConstants.TO_CLIENT.ADD_TO_CONTAINER + ct.getUUID() + "|" + SerializationUtils.objectToString(c));
                }
            }

            @Override
            public void removeControl(LControl c, LContainer ct, UUID viewer) {
                Player p = Bukkit.getPlayer(viewer);
                if (p != null && ct instanceof LControl) {
                    playerManager.getPlayer(p).sendLithiumMessage(LithiumConstants.TO_CLIENT.REMOVE_FROM_CONTAINER + ct.getUUID() + "|" + c.getUUID());
                }
            }
        };
        LithiumConstants.onClose = (c, viewer) -> playerManager.getPlayer(Bukkit.getPlayer(viewer)).closeInterface();
        //We need to register the incoming message plugin message!
        Bukkit.getMessenger().registerIncomingPluginChannel(this, LITHIUM_CHANNEL, new LithiumListener(lithiumPacketHandler, playerManager));
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, LITHIUM_CHANNEL);
        Bukkit.getPluginManager().registerEvents(new PlayerEvents(playerManager), this);
    }

    @Override
    public void onDisable() {
        instance = null;
        LithiumConstants.onRefresh = null;
        LithiumConstants.onClose = null;
        LithiumConstants.onControlRuntime = null;
    }
}
