package com.tewinsid.proxyPool.dao;

import java.util.Collection;
import java.util.List;

public interface RedisOperation {
    List<String> get(long count);

    void put(String proxy);

    String pop();

    Long size();

    void remove(String value);

    boolean exist(String value);
}
