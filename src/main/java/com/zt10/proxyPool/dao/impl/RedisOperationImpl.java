package com.zt10.proxyPool.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class RedisOperationImpl implements com.zt10.proxyPool.dao.RedisOperation {

    @Autowired
    private RedisTemplate<String, String> template;

    @Resource(name="redisTemplate")
    private ListOperations<String, String> pool;


    private final String KEY = "proxy";

    @Override
    public List get(long count) {
        List<String> proxys = pool.range(KEY, 0, count - 1);
        pool.trim(KEY, count, -1);
        return proxys;
    }

    @Override
    public void put(String proxy) {
        pool.rightPush(KEY, proxy);
    }

    @Override
    public String pop() {
        return pool.leftPop(KEY);
    }

    @Override
    public Long size() {
        return pool.size(KEY);
    }
}
