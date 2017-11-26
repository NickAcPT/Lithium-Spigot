package net.nickac.lithium.frontend.pluginchannel.packets.abstracts;

import net.nickac.lithium.frontend.LithiumPlugin;
import net.nickac.lithium.frontend.LithiumUtils;
import net.nickac.lithium.frontend.players.LithiumPlayer;
import net.nickac.lithium.frontend.pluginchannel.packets.PacketMap;
import net.nickac.lithium.frontend.pluginchannel.packets.in.*;

import java.util.Arrays;
import java.util.List;

public class PacketHandlerImpl implements PacketHandler {

    private LithiumPlayer lithiumPlayer;

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

    public PacketHandlerImpl(LithiumPlayer lithiumPlayer) {
        this.lithiumPlayer = lithiumPlayer;
    }


    @Override
    public void read(Message message) {
        PacketIn packetIn = PacketMap.instance.getByString(message.getKey());
        if(packetIn!=null){
            packetIn.execute(lithiumPlayer, message.getData());
        }
    }

    @Override
    public void write(PacketOut packetOut) {
        lithiumPlayer.getHandle().sendPluginMessage(
                LithiumPlugin.getInstance(),
                LithiumPlugin.LITHIUM_CHANNEL,
                LithiumUtils.writeUTF8String(String.join("|", packetOut.execute()))
        );
    }
}
