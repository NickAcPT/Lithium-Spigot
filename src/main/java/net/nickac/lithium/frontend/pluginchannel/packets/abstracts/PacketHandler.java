package net.nickac.lithium.frontend.pluginchannel.packets.abstracts;

import net.nickac.lithium.frontend.pluginchannel.packets.abstracts.Message;

public interface PacketHandler {

    void handlePacket(Message message);

}
