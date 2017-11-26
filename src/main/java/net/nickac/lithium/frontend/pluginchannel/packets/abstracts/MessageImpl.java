package net.nickac.lithium.frontend.pluginchannel.packets.abstracts;

import net.nickac.lithium.frontend.players.LithiumPlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class MessageImpl implements Message {

    private LithiumPlayer player;
    private String key;
    private List<String> data;

    public MessageImpl(LithiumPlayer player, String key, List<String> data) {
        this.player = player;
        this.key = key;
        this.data = data;
    }

    @Override
    public LithiumPlayer getPlayer() {
        return player;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public List<String> getData() {
        return data;
    }
}
