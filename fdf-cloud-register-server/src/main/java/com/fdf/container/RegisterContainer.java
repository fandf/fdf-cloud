package com.fdf.container;

import com.fdf.entity.ReqInfoEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册容器
 *
 * @author fandongfeng
 * @date 2022/11/24 10:09
 */
@Component
public class RegisterContainer {

    private static final Map<String, List<String>> MAP_ADDRESS = new ConcurrentHashMap<>();

    public boolean put(ReqInfoEntity entity) {
        String serviceName = entity.getServiceName();
        //1.先判断是否注册过容器
        List<String> addressList = MAP_ADDRESS.computeIfAbsent(serviceName, k -> new ArrayList<>());
        return addressList.add(entity.getServiceAddress());
    }

    public Map<String, List<String>> getMapAddress() {
        return MAP_ADDRESS;
    }


}
