package com.zt10.proxyPool.pool.schedule;

import com.zt10.proxyPool.dao.RedisOperation;
import com.zt10.proxyPool.exception.ThreadPoolException;
import com.zt10.proxyPool.utils.NetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Component
public class Tester {


    @Autowired
    public RedisOperation redisOperation;

    ExecutorService exec = new ThreadPoolExecutor(10,
            10,
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

    private void availableDetection(final String proxy, final String prefix) {
        exec.execute(new AvailableRunnable(proxy, prefix));
    }

    /**
     * 因为Spring本身默认Bean为单例模式构建，同时是非线程安全的，因此禁止了在Thread子类中的注入行为，
     * 因此在Thread中直接注入的bean是null的，会发生空指针错误。
     * 这里使用内部列形式访问外部类spring注入的redisOperation
     * 感觉通过参数每次创建线程更新线程类变量的方式太脑残了
     */
    class AvailableRunnable implements Runnable {


        private String proxy;

        private String prefix;

        private final String URL = "http://www.baidu.com";


        public AvailableRunnable(String proxy, String prefix) {
            this.setPrefix(prefix);
            this.setProxy(proxy);
        }

        @Override
        public void run() {
            String proxy = this.getProxy();
            String prefix = this.getPrefix();
            String[] proxyMessage = proxy.split(":");
            try {
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


        public synchronized String getProxy() {
            return proxy;
        }

        public synchronized void setProxy(String proxy) {
            this.proxy = proxy;
        }

        public synchronized String getPrefix() {
            return prefix;
        }

        public synchronized void setPrefix(String prefix) {
            this.prefix = prefix;
        }

    }
}


