package com.fdf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

/**
 * @author fandongfeng
 * @date 2022/11/24 10:28
 */
@Data
@AllArgsConstructor(staticName = "of")
public class ServiceInstance {

    /**
     * 服务ip
     */
    private String serviceAddress;
    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 最后续约时间
     */
    private long lastRenewalTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceInstance that = (ServiceInstance) o;
        return serviceAddress.equals(that.serviceAddress) && serviceName.equals(that.serviceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceAddress, serviceName);
    }
}
