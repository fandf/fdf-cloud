package com.fdf.container;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.fdf.constant.RegisterConstant;
import com.fdf.entity.ServiceInstance;
import com.fdf.request.ReqInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    private static volatile Map<String, HashSet<ServiceInstance>> mapServiceInstances = new ConcurrentHashMap<>();

    /**
     * 定时心跳检测服务是否正常
     * 当前系统时间-最后续约时间 > 5s 服务剔除
     */
    private ScheduledExecutorService scheduledService = Executors.newSingleThreadScheduledExecutor();

    public RegisterContainer() {
        scheduledService.scheduleAtFixedRate(new Heartbeat(), 5, 5, TimeUnit.SECONDS);
    }

    public boolean put(ReqInfoDTO dto) {
        String serviceName = dto.getServiceName();
        //1.先判断是否注册过容器
        HashSet<ServiceInstance> serviceInstances = mapServiceInstances.get(serviceName);
        //双重锁检查
        if (serviceInstances == null) {
            synchronized (this) {
                if (mapServiceInstances.get(serviceName) == null) {
                    serviceInstances = new HashSet<>();
                    mapServiceInstances.put(serviceName, serviceInstances);
                }
            }
        }
        ServiceInstance serviceInstance = ServiceInstance.of(dto.getServiceAddress(), dto.getServiceName(), System.currentTimeMillis());
        return serviceInstances.add(serviceInstance);
    }

    public Map<String, HashSet<ServiceInstance>> getMapAddress() {
        return mapServiceInstances;
    }

    public ServiceInstance remove(ReqInfoDTO dto) {
        return findByServiceInstance(dto, true);
    }

    private ServiceInstance findByServiceInstance(ReqInfoDTO dto, boolean isDelete) {
        String serviceAddress = dto.getServiceAddress();
        if (StrUtil.isBlank(serviceAddress)) {
            return null;
        }
        HashSet<ServiceInstance> serviceInstances = mapServiceInstances.get(dto.getServiceName());
        if (CollUtil.isEmpty(serviceInstances)) {
            log.error("serviceInstances not found");
            return null;
        }
        for (ServiceInstance serviceInstance : serviceInstances) {
            if (serviceInstance.getServiceAddress().equals(serviceAddress)) {
                if (isDelete) {
                    serviceInstances.remove(serviceInstance);
                }
                return serviceInstance;
            }
        }
        return null;
    }


    /**
     * 服务续约
     *
     * @param dto
     * @return
     */
    public boolean renewal(ReqInfoDTO dto) {
        ServiceInstance instance = findByServiceInstance(dto, false);
        if (instance == null) {
            //服务不存在，注册
            return put(dto);
        }
        instance.setLastRenewalTime(instance.getLastRenewalTime() + dto.getRenewalDuration());
        return true;
    }

    /**
     * 检测注册中心是否宕机
     */
    static class Heartbeat implements Runnable {

        @Override
        public void run() {
            mapServiceInstances.forEach((k, v) -> {
                HashSet<ServiceInstance> serviceInstances = mapServiceInstances.get(k);
                //foreach中删除会报错
                v.forEach(instance -> {
                    if (System.currentTimeMillis() - instance.getLastRenewalTime() > RegisterConstant.REMOVE_INSTANCE) {
                        serviceInstances.remove(instance);
                    }
                });
            });
        }
    }

}
