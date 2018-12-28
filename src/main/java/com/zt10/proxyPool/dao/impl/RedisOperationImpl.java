package com.zt10.proxyPool.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class RedisOperationImpl implements com.zt10.proxyPool.dao.RedisOperation {

    @Resource
    RedisTemplate redisTemplate;

    private ListOperations<String, String> pool;


    private final String KEY = "proxy";

    @Override
    public List get(long count) {
        pool = redisTemplate.opsForList();
        List<String> proxys = pool.range(KEY, 0, count - 1);
        pool.trim(KEY, count, -1);
        return proxys;
    }

    @Override
    public void put(String proxy) {
        pool = redisTemplate.opsForList();
        pool.rightPush(KEY, proxy);
    }

    @Override
    public String pop() {
        pool = redisTemplate.opsForList();
        return pool.leftPop(KEY);
    }

    @Override
    public Long size() {
        pool = redisTemplate.opsForList();
        return pool.size(KEY);
    }
}
