package com.zt10.proxyPool.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

@Component
public class RedisOperationImpl implements com.zt10.proxyPool.dao.RedisOperation {

    @Autowired
    private RedisTemplate<String, String> template;

    @Resource(name = "redisTemplate")
    private SetOperations<String, String> pool;


    private final String KEY = "proxy";

    @Override
    public List<String> get(long count) {
        List<String> proxys = pool.randomMembers(KEY, count);
        return proxys;
    }

    @Override
    public void put(String proxy) {
        pool.add(KEY, proxy);
    }

    @Override
    public String pop() {
        return pool.pop(KEY);
    }

    @Override
    public Long size() {
        return pool.size(KEY);
    }

    @Override
    public void remove(String value) {
        pool.remove(KEY, value);
    }


}
