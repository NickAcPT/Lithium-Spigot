package net.nickac.lithium.frontend.pluginchannel.packets;

import net.nickac.lithium.frontend.pluginchannel.packets.abstracts.PacketIn;

import java.util.HashMap;
import java.util.Map;

public class PacketMap {

    public final static PacketMap instance = new PacketMap();

    private PacketMap() {
    }

    private final Map<String, PacketIn> classStringMap = new HashMap<>();

    public void registerPacketIn(String key, PacketIn packet) {
        classStringMap.put(key, packet);
    }

    public PacketIn getByString(String keyString) {
        return classStringMap.get(keyString);
    }


}
