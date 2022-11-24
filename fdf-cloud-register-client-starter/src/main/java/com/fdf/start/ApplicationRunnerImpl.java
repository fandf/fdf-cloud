package com.fdf.start;

import com.fdf.service.RegisterClientService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import javax.annotation.Resource;

/**
 * springboot 项目启动后回调
 *
 * @author fandongfeng
 * @date 2022/11/24 14:40
 */
public class ApplicationRunnerImpl implements ApplicationRunner {

    @Resource
    RegisterClientService registerClientService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //服务注册
        registerClientService.register();
    }
}
