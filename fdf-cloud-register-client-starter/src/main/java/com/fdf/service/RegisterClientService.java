package com.fdf.service;

import com.alibaba.fastjson2.JSONObject;
import com.fdf.config.CloudConfig;
import com.fdf.config.ServerConfig;
import com.fdf.utils.HttpClientUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Resource;

/**
 * @author fandongfeng
 * @date 2022/11/24 14:41
 */
public class RegisterClientService {

    private static final Log log = LogFactory.getLog(RegisterClientService.class);

    @Resource
    ServerConfig serverConfig;
    @Resource
    CloudConfig cloudConfig;

    public void register() {
        JSONObject data = getRequestParameter();
        JSONObject jsonObject = HttpClientUtils.httpPost(cloudConfig.getServerAddress() + "/fdf/register", data);
        log.info("服务注册结果: " + jsonObject);
    }

    public void remove() {
        JSONObject data = getRequestParameter();
        JSONObject jsonObject = HttpClientUtils.httpPost(cloudConfig.getServerAddress() + "/fdf/remove", data);
        log.info("服务下线结果: " + jsonObject);
    }

    private JSONObject getRequestParameter() {
        String serviceAddress = serverConfig.getUrl();
        String serviceName = cloudConfig.getServiceName();
        JSONObject data = new JSONObject();
        data.put("serviceName", serviceName);
        data.put("serviceAddress", serviceAddress);
        return data;
    }

}
