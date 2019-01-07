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

    @Scheduled(cron = "* */1 * * * *")
    public void poolSchedule() {
        System.out.println("process is running");
        adder.lengthDetection();
        tester.testRightHalf();
    }
}
