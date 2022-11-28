package com.fdf.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author dongfengfan
 */
@EnableConfigurationProperties(value = {CloudClientConfig.class})
@ConfigurationProperties(prefix = "fdf.cloud")
public class CloudClientConfig {
    /**
     * 配置分布式配置中心 服务器端地址
     */
    private String serverConfigUrl;

    public String getServerConfigUrl() {
        return serverConfigUrl;
    }

    public void setServerConfigUrl(String serverConfigUrl) {
        this.serverConfigUrl = serverConfigUrl;
    }
}