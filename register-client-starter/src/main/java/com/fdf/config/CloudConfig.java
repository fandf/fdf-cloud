package com.fdf.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 配置文件
 *
 * @author fandongfeng
 * @date 2022/11/24 14:24
 */
@EnableConfigurationProperties(value = {CloudConfig.class})
@ConfigurationProperties(prefix = "fdf.cloud")
public class CloudConfig {

    /**
     * 服务注册中心地址
     */
    private String serverAddress;
    /**
     * 服务的名称
     */
    private String serviceName;

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
