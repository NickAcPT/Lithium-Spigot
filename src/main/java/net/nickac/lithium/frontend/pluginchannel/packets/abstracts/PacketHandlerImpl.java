package net.nickac.lithium.frontend.pluginchannel.packets.abstracts;

import net.nickac.lithium.frontend.pluginchannel.packets.PacketMap;
import net.nickac.lithium.frontend.pluginchannel.packets.in.*;

import java.util.Arrays;
import java.util.List;

public class PacketHandlerImpl implements PacketHandler {

    static {
        List<PacketIn> packetIns = Arrays.asList(
                new ButtonAction(),
                new LithiumOk(),
                new SliderValueChanged(),
                new TextboxTextChanged(),
                new ToggleAction(),
                new WindowClose(),
                new WindowOpen());
        packetIns.forEach((p) -> PacketMap.instance.registerPacketIn(p.key(), p));
    }


    @Override
    public void handlePacket(Message message) {
        PacketIn packetIn = PacketMap.instance.getByString(message.getKey());
        if(packetIn!=null){
            packetIn.execute(message.getPlayer(),message.getData());
        }
    }
}
