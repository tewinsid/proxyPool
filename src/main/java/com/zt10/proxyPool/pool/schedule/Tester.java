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

    ExecutorService exec = new ThreadPoolExecutor(10,
            10,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());

    public void testRightHalf() {
        System.out.println("tester is running");
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
        exec.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String[] proxyMessage = proxy.split(":");
                    boolean flag = NetUtil.get(URL, proxyMessage[0], proxyMessage[1]);
                    if (flag) {
                        System.out.println(proxy + " is available");
                        redisOperation.put(proxy);
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
