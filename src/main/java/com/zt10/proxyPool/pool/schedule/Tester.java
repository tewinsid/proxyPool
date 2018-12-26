package com.zt10.proxyPool.pool.schedule;

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

@Component
public class Tester {
    @Autowired
    private RedisTemplate redisTemplate;

    private final String URL = "http://www.baidu.com";

    private final String KEY = "proxy";

    public void testRightHalf() {
        ListOperations<String, String> pool = redisTemplate.opsForList();
        List<String> proxys = pool.range(KEY, 0, pool.size("proxy") / 2);
        Iterator<String> it = proxys.iterator();
        while (it.hasNext()) {
            //TODO 此处应使用线程池
            String proxy = it.next();
            if (!availableDetection(proxy)) {
                it.remove();
            }
            pool.remove(KEY, 0, proxy);
        }
    }

    private boolean availableDetection(String proxy) {
        String[] proxyMessage = proxy.split(":");
        return NetUtil.get(URL, proxyMessage[0], proxyMessage[1]);
    }

}
