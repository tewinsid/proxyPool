package com.zt10.proxyPool;

import com.zt10.proxyPool.pool.Getter;
import com.zt10.proxyPool.pool.schedule.Adder;
import org.junit.Test;

import java.util.List;

public class GetterTest {
    Getter getter = new Getter();

    @Test
    public void freeProxyTest() {
        List list = getter.freeProxy();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    @Test
    public void adderTest() {
        Adder adder = new Adder();
        adder.lengthDetection();
    }
}
