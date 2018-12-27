package com.zt10.proxyPool.pool.schedule;

import com.zt10.proxyPool.dao.RedisOperation;
import com.zt10.proxyPool.utils.NetUtil;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Component
public class Tester {
    private final String URL = "http://www.baidu.com";

    @Autowired
    public RedisOperation redisOperation;


    public void testRightHalf() {
        List proxys = redisOperation.get(redisOperation.size() / 2);
        proxysTestAndPut(proxys);
    }

    public void proxysTestAndPut(List proxys) {
        Iterator<String> it = proxys.iterator();
        while (it.hasNext()) {
            String proxy = it.next();
            availableDetection(proxy);
        }
    }

    private void availableDetection(String proxy) {
        //TODO 此处应使用线程池
        String[] proxyMessage = proxy.split(":");
        boolean flag = NetUtil.get(URL, proxyMessage[0], proxyMessage[1]);
        if (flag) {
            redisOperation.put(proxy);
        }
    }

}
