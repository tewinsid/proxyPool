package com.zt10.proxyPool.pool;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Schedule {

    @Scheduled(fixedRate = 20000)
    public void poolSchedule() {

    }
}
