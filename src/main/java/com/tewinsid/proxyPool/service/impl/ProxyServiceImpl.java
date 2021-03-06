package com.tewinsid.proxyPool.service.impl;

import com.tewinsid.proxyPool.dao.RedisOperation;
import com.tewinsid.proxyPool.service.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProxyServiceImpl implements ProxyService {

    @Autowired
    private RedisOperation redisOperation;

    @Override
    public List getProxys(int number) {
        List<String> proxys = redisOperation.get(number);
        List result = new ArrayList(number);
        for (int i = 0; i < proxys.size(); i++) {
            String[] msg = proxys.get(i).split(":");
            Map temp = new HashMap();
            temp.put("ip", msg[0]);
            temp.put("port", msg[1]);
            result.add(temp);
        }
        return result;
    }
}
