package com.zt10.proxyPool.pool;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Getter {

    public List getProxys() {
        return new ArrayList();
    }
}
