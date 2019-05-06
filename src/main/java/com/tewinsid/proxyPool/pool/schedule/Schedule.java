package com.tewinsid.proxyPool.pool.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Schedule {

    @Autowired
    public Adder adder;

    @Autowired
    public Tester tester;

    @Scheduled(cron = "* */3 * * * ?")
    public void adderSchedule() {
        adder.lengthDetection();
    }

    @Scheduled(cron = "* */2 * * * ?")
    public void testerSchedule() {
        tester.testRightHalf();
    }
}
