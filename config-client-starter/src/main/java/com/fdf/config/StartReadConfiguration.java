package com.fdf.config;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.fdf.config.constant.Constant;
import com.fdf.config.service.ConfigClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.StandardEnvironment;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * @author dongfengfan
 */
public class StartReadConfiguration implements EnvironmentAware, ApplicationListener<ContextRefreshedEvent> {
    private StandardEnvironment environment;
    private FdfBeanDefinitionRegistry fdfBeanDefinitionRegistry;
    private BeanDefinitionRegistry beanDefinitionRegistry;
    @Autowired
    private ConfigurableApplicationContext applicationContext;
    @Autowired
    private ConfigClientService configClientService;
    private ScheduledExecutorService scheduledService = Executors.newSingleThreadScheduledExecutor();

    public void init() {
        fdfBeanDefinitionRegistry = (FdfBeanDefinitionRegistry) applicationContext.getBeanFactory().getBean("fdfBeanDefinitionRegistry");
        beanDefinitionRegistry = fdfBeanDefinitionRegistry.getBeanDefinitionRegistry();
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.forEach(propertySource -> {
            if (propertySource instanceof OriginTrackedMapPropertySource) {
                OriginTrackedMapPropertySource mapPropertySource
                        = (OriginTrackedMapPropertySource) propertySource;
                // ??????rest??????????????????????????????????????? ??????????????????
                JSONObject data = configClientService.rpcHttpGetConfig();
                if (data != null) {
                    JSONArray config = data.getJSONArray("config");
                    for (int i = 0; i < config.size(); i++) {
                        JSONObject configInfo = config.getJSONObject(i);
                        mapPropertySource.getSource().put(configInfo.getString("key"),
                                configInfo.getString("value"));
                    }
                    // ??????????????? ????????????????????? ???md5?????????
                    String md5 = data.getString("md5");
                    refreshBean(md5);
                }

            }

        });
        // ?????????????????????????????????????????????????????????
        scheduledService.scheduleAtFixedRate(new CheckConfigurationFileThread(), 3, 3, TimeUnit.SECONDS);

    }


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (StandardEnvironment) environment;
    }

    private void refreshBean(String md5) {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefinition(beanDefinitionName);
            if (Constant.SCOPE_NAME.equals(beanDefinition.getScope())) {
                applicationContext.getBeanFactory().destroyBean(beanDefinitionName);
                // ?????????bean
                RefreshScope.getBeans().remove(beanDefinitionName);
                Object bean = applicationContext.getBean(beanDefinitionName);
            }
        }
        ConfigMd5.setMd5(md5);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        init();
    }

    class CheckConfigurationFileThread implements Runnable {

        @Override
        public void run() {
            JSONObject result = configClientService.md5Comparison();
            if (result == null) {
                return;
            }
            Integer code = result.getInteger("code");
            if (code != 201) {
                return;
            }
            JSONObject data = result.getJSONObject("data");
            String md5 = data.getString("md5");
            MutablePropertySources propertySources = environment.getPropertySources();
            propertySources.forEach(propertySource -> {
                if (propertySource instanceof OriginTrackedMapPropertySource) {
                    OriginTrackedMapPropertySource mapPropertySource
                            = (OriginTrackedMapPropertySource) propertySource;
                    // ????????????????????? ???????????????????????????  ????????????

                    JSONArray config = data.getJSONArray("config");
                    for (int i = 0; i < config.size(); i++) {
                        JSONObject configInfo = config.getJSONObject(i);
                        mapPropertySource.getSource().put(configInfo.getString("key"),
                                configInfo.getString("value"));
                    }
                    refreshBean(md5);
                }
            });
        }
    }

}
