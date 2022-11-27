package com.fdf.service;

import com.alibaba.fastjson2.JSONObject;
import com.fdf.config.CloudConfig;
import com.fdf.config.ServerConfig;
import com.fdf.utils.HttpClientUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Lazy;

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
    @Resource
    @Lazy
    RenewalService renewalService;

    public void register() {
        //注册失败 重试策略
        while (true) {
            JSONObject data = getRequestParameter();
            JSONObject result = HttpClientUtils.httpPost(cloudConfig.getServerAddress() + "/fdf/register", data);
            log.info("服务注册结果: " + result);
            if (result != null && result.getInteger("code") == 200) {
                renewalService.start();
                break;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

    public void renewal() {
        JSONObject data = getRequestParameter();
        //续约五秒，可放配置文件
        data.put("renewalDuration", 5000);
        JSONObject jsonObject = HttpClientUtils.httpPost(cloudConfig.getServerAddress() + "/fdf/renewal", data);
        log.info("服务续约结果: " + jsonObject);
    }
}
