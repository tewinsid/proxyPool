package com.zt10.proxyPool;

import com.zt10.proxyPool.pool.Getter;
import com.zt10.proxyPool.pool.schedule.Adder;
import org.junit.Test;

import java.util.List;

public class GetterTest {
    Getter getter = new Getter();

    //@Test
    public void getProxyTest() {
        List raw_proxys = getter.getProxys();
        System.out.println("size ---- " + raw_proxys.size());
    }

    //@Test
    public void freeProxyTest() {
        List list = getter.freeProxy();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + "  ---  " +list.get(i));
        }
    }

    //@Test
    public void xicidailiProxyTest() {
        List list = getter.xicidailiProxy();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + "  ---  " +list.get(i));
        }
    }

    //@Test
    public void wuyouProxyTest() {
        List list = getter.wuyouProxy();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + "  ---  " +list.get(i));
        }
    }

    //@Test
    public void xroxyProxyTest() {
        List list = getter.xroxyProxy();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + "  ---  " +list.get(i));
        }
    }

}
