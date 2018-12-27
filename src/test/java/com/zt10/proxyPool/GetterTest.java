package com.zt10.proxyPool;

import com.zt10.proxyPool.pool.Getter;
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
}
