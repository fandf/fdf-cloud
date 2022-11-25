package com.fdf.callback;

import com.fdf.service.RegisterClientService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import javax.annotation.Resource;

/**
 * @author fandongfeng
 * @date 2022/11/24 21:04
 */
public class CloseCallbackImpl implements ApplicationListener<ContextClosedEvent> {

    private static final Log log = LogFactory.getLog(CloseCallbackImpl.class);

    @Resource
    RegisterClientService registerClientService;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        registerClientService.remove();
        log.info("service client stop");
    }
}
