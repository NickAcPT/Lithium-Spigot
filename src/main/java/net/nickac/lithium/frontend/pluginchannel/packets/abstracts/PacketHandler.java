package net.nickac.lithium.frontend.pluginchannel.packets.abstracts;

public interface PacketHandler {
    void read(Message message);

    void write(PacketOut packetOut);
}
