package com.fdf.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Resource;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author fandongfeng
 * @date 2022/11/25 10:26
 */
public class RenewalService {

    private static final Log log = LogFactory.getLog(RenewalService.class);

    private ScheduledExecutorService scheduledService = Executors.newSingleThreadScheduledExecutor();

    /**
     * 服务注册完成之后再续约
     */
    public void start() {
        log.info("服务续约启动成功");
        scheduledService.scheduleAtFixedRate(new RenewalThread(), 5, 5, TimeUnit.SECONDS);
    }

    @Resource
    RegisterClientService registerClientService;

    private class RenewalThread implements Runnable {

        @Override
        public void run() {
            registerClientService.renewal();
        }
    }
}
