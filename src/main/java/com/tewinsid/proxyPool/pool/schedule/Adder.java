package com.tewinsid.proxyPool.pool.schedule;

import com.tewinsid.proxyPool.dao.RedisOperation;
import com.tewinsid.proxyPool.pool.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class Adder {
    private long LOWERLIMIT = 50;

    @Autowired
    public RedisOperation redisOperation;

    @Autowired
    public Tester tester;

    @Autowired
    public Getter getter;

    public void lengthDetection() {
        while (redisOperation.size() < LOWERLIMIT) {
            List raw_proxys = getter.getProxys();

            System.out.println("fetch " + raw_proxys.size() + "from internet");

            tester.proxysTestAndPut(raw_proxys, "internet");

            //防止目标网站封ip，每隔1分钟爬取一次
            try {
                TimeUnit.MINUTES.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("sleep exception");
                continue;
            }
        }
    }
}
