package com.fdf.container;

import com.fdf.entity.ServiceInstance;
import com.fdf.request.ReqInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 注册容器
 *
 * @author fandongfeng
 * @date 2022/11/24 10:09
 */
@Component
@Slf4j
public class RegisterContainer {

    /**
     * 服务注册数据
     */
    private static volatile Map<String, HashSet<ServiceInstance>> MAP_ADDRESS = new ConcurrentHashMap<>();

    /**
     * 定时心跳检测服务是否正常
     */
    private ScheduledExecutorService scheduledService = Executors.newSingleThreadScheduledExecutor();

    public boolean put(ReqInfoDTO dto) {
        String serviceName = dto.getServiceName();
        //1.先判断是否注册过容器
        HashSet<ServiceInstance> serviceInstances = MAP_ADDRESS.get(serviceName);
        //双重锁检查
        if(serviceInstances == null) {
            synchronized (this) {
                if(MAP_ADDRESS.get(serviceName) == null) {
                    serviceInstances = new HashSet<>();
                    MAP_ADDRESS.put(serviceName, serviceInstances);
                }
            }
        }
        ServiceInstance serviceInstance = ServiceInstance.of(dto.getServiceAddress(), dto.getServiceName(), System.currentTimeMillis());
        return serviceInstances.add(serviceInstance);
    }

    public Map<String, HashSet<ServiceInstance>> getMapAddress() {
        return MAP_ADDRESS;
    }


}
