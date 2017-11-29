package net.nickac.lithium.frontend.pluginchannel.packets.abstracts;

import net.nickac.lithium.frontend.players.LithiumPlayer;

import java.util.List;

public interface Message {

    LithiumPlayer getPlayer();

    String getKey();

    List<String> getData();

}
