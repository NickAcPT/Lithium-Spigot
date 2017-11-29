package net.nickac.lithium.frontend.container;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.nickac.lithium.backend.controls.LContainerViewable;
import net.nickac.lithium.frontend.pluginchannel.packets.abstracts.PacketOut;

import java.lang.reflect.Constructor;

public class ContainerMap {


    private final BiMap<Class<? extends LContainerViewable>, Constructor<? extends PacketOut>> classStringMap;

    public ContainerMap() {
        classStringMap = HashBiMap.create();
    }

    public void registerContainer(Class<? extends LContainerViewable> key, Class<? extends PacketOut> value) {

        try {
            Constructor<? extends PacketOut> constructor = value.getConstructor(key);
            classStringMap.put(key, constructor);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public Constructor<? extends PacketOut> getByClass(Class<? extends LContainerViewable> lClass) {
        return classStringMap.get(lClass);
    }



}
