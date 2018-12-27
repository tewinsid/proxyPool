package com.zt10.proxyPool.pool.schedule;

import com.zt10.proxyPool.dao.RedisOperation;
import com.zt10.proxyPool.pool.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
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
            tester.proxysTestAndPut(raw_proxys);
        }
        System.out.println(" proxy pool is enough");
    }
}
