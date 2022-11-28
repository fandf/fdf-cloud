package com.fdf.config;

import com.fdf.config.service.ConfigClientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author dongfengfan
 */
@Configuration
public class CloudAutoConfiguration {

    @Bean
    public FdfBeanDefinitionRegistry mayiktBeanDefinitionRegistry() {
        return new FdfBeanDefinitionRegistry();
    }

    @Bean
    public RefreshScope refreshScope() {
        return new RefreshScope();
    }

    @Bean
    public ConfigClientService configClientService() {
        return new ConfigClientService();
    }

    @Bean
    public StartReadConfiguration startReadConfiguration() {
        return new StartReadConfiguration();
    }

    @Bean
    public CloudClientConfig cloudClientConfig() {
        return new CloudClientConfig();
    }
}
