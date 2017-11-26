

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

import net.nickac.lithium.frontend.LithiumUtils;
import net.nickac.lithium.frontend.players.LithiumPlayerManager;
import net.nickac.lithium.frontend.pluginchannel.packets.abstracts.MessageImpl;
import net.nickac.lithium.frontend.pluginchannel.packets.abstracts.PacketHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by NickAc for Lithium!
 */
public class LithiumListener implements PluginMessageListener {

    private PacketHandler packetHandler;
    private LithiumPlayerManager lithiumPlayerManager;

    public LithiumListener(PacketHandler packetHandler, LithiumPlayerManager lithiumPlayerManager) {
        this.packetHandler = packetHandler;
        this.lithiumPlayerManager = lithiumPlayerManager;
    }

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        String message = LithiumUtils.readUTF8String(bytes).trim();
        String[] msgArray = message.split("\\|");
        String key = msgArray[0];

        List<String> data = new ArrayList<>();
        if (msgArray.length > 1) {
            List<String> par = Arrays.asList(msgArray).subList(1, msgArray.length);
            data.addAll(par);
        }
        packetHandler.handlePacket(new MessageImpl(lithiumPlayerManager.getPlayer(player), key, data));
    }
}
