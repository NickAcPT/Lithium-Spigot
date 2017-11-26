package net.nickac.lithium.frontend.pluginchannel.packets.abstracts;

import java.util.List;

public interface PacketOut {
    List<String> execute();
    String key();
}
