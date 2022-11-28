package com.fdf.config.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.fdf.config.CloudClientConfig;
import com.fdf.config.ConfigMd5;
import com.fdf.config.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/11/29 06:54
 */
public class ConfigClientService implements EnvironmentAware {

    private StandardEnvironment environment;

    @Autowired
    private CloudClientConfig cloudClientConfig;

    /**
     * spring ioc容器加载完成之后---
     * 就需要 rest请求 连接到 分布式配置中心
     * Server端 获取配置文件内容
     */
    public JSONObject rpcHttpGetConfig() {
        /**
         * 获取项目中 引入该jar包 服务的名称
         */
        String applicationName = environment.getProperty("spring.application.name");
        // 发送rest请求获取 配置文件内容
        Map<String, String> mapParam = new HashMap<>();
        mapParam.put("name", applicationName);
        JSONObject result = HttpClientUtils.httpGet(cloudClientConfig.getServerConfigUrl() + "getConfig", mapParam);
        if(result == null) {
            return null;
        }
        Integer code = result.getInteger("code");
        if (code != 200) {
            // 输出日志
            return null;
        }
        JSONObject data = result.getJSONObject("data");
        return data;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (StandardEnvironment) environment;
    }

    /**
     * 发送请求  验证本地与服务器端MD5值是否一致的
     *
     * @return
     */
    public JSONObject md5Comparison() {
        String applicationName = environment.getProperty("spring.application.name");
        if (StrUtil.isEmpty(applicationName)) {
            return null;
        }
        HashMap<String, String> mapParam = new HashMap<>();
        mapParam.put("name", applicationName);
        mapParam.put("md5", ConfigMd5.getMd5());
        return HttpClientUtils.httpGet(cloudClientConfig.getServerConfigUrl() + "md5Comparison", mapParam);
    }
}
