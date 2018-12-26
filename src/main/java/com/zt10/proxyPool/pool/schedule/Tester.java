package com.zt10.proxyPool.pool.schedule;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Tester {
    @Autowired
    private RedisTemplate redisTemplate;

    public void getIP() {
        ListOperations<String, String> pool = redisTemplate.opsForList();
        List proxys = pool.range("proxy", 0, pool.size("proxy") / 2);
        for (int i = 0; i < proxys.size(); i++) {

        }
    }

    private boolean test(String proxy) {
        return false;

    }

}
