package com.zt10.proxyPool.dao;

import java.util.List;

public interface RedisOperation {
    List<String> get(long count);

    void put(String proxy);

    String pop();

    Long size();
}
