package com.zt10.proxyPool.pool.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Schedule {

    @Autowired
    public Adder adder;

    @Autowired
    public Tester tester;

    @Scheduled(fixedRate = 20000)
    public void poolSchedule() {
        System.out.println(" poolSchedule is running");
        adder.lengthDetection();
        tester.testRightHalf();
    }
}
