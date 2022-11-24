package com.fdf.config;

import com.fdf.service.RegisterClientService;
import com.fdf.start.ApplicationRunnerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动注册配置
 *
 * @author fandongfeng
 * @date 2022/11/24 14:26
 */
@Configuration
public class CloudAutoConfiguration {

    @Bean
    public CloudConfig cloudConfig() {
        return new CloudConfig();
    }

    /**
     * 获取ip、端口号
     */
    @Bean
    public ServerConfig serverConfig() {
        return new ServerConfig();
    }

    /**
     * 获取启动后的监听
     */
    @Bean
    public ApplicationRunnerImpl applicationRunnerImpl() {
        return new ApplicationRunnerImpl();
    }

    @Bean
    public RegisterClientService registerClientService() {
        return new RegisterClientService();
    }

}
