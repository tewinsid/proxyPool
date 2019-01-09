package com.zt10.proxyPool.pool.schedule;

import com.zt10.proxyPool.dao.RedisOperation;
import com.zt10.proxyPool.exception.ThreadPoolException;
import com.zt10.proxyPool.utils.NetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

@Component
public class Tester {
    private final String URL = "http://www.baidu.com";

    @Autowired
    public RedisOperation redisOperation;

    ExecutorService exec = new ThreadPoolExecutor(8,
            8,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());

    public void testRightHalf() {
        List proxys = redisOperation.get(redisOperation.size() / 2);
        System.out.println("get " + proxys.size() + " from redis");
        proxysTestAndPut(proxys, "redis");
    }

    public void proxysTestAndPut(List proxys, String prefix) {
        Iterator<String> it = proxys.iterator();
        while (it.hasNext()) {
            String proxy = it.next();
            boolean condition = "redis".equals(prefix) ||
                    ("internet".equals(prefix) && !redisOperation.exist(proxy));
            if (condition) {
                availableDetection(proxy, prefix);
            }
        }
    }

    private void availableDetection(final String proxy,final String prefix) {
        exec.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String[] proxyMessage = proxy.split(":");
                    boolean flag = NetUtil.get(URL, proxyMessage[0], proxyMessage[1]);
                    if (flag) {
                        redisOperation.put(proxy);
                        System.out.println(prefix + "  ---  " + proxy + "  is available");
                    } else {
                        redisOperation.remove(proxy);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ThreadPoolException("");
                }

            }
        });
    }

}
