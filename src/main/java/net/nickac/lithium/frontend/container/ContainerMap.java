package net.nickac.lithium.frontend.container;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.nickac.lithium.backend.controls.LContainerViewable;

public class ContainerMap {

    public final static ContainerMap instance = new ContainerMap();

    private ContainerMap(){
    }

    private final BiMap<Class<? extends LContainerViewable>,String> classStringMap = HashBiMap.create();

    public void registerContainer(Class<? extends LContainerViewable> key, String value){
        classStringMap.put(key,value);
    }

    public String getByClass(Class<? extends LContainerViewable> lClass){
        return classStringMap.get(lClass);
    }

    public Class<? extends LContainerViewable> getByString(String keyString){
        return classStringMap.inverse().get(keyString);
    }


}
