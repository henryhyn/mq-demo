package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by henry on 2018/9/15.
 */
@Slf4j
@Component
public class UtAllocateWorker implements InitializingBean {
    @Autowired
    private UtTaskConsumer utTaskConsumer;

    @Autowired
    private UtUserConsumer utUserConsumer;

    @Override
    public void afterPropertiesSet() throws Exception {
        for (int i = 0; i < 10; i++) {
            log.info("{} => {}", utUserConsumer.getUser(), utTaskConsumer.getTask());
            Thread.sleep(5000L);
        }
    }
}
