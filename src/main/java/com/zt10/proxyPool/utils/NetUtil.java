package com.zt10.proxyPool.utils;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.junit.Test;

public class NetUtil {
    CloseableHttpClient httpclient;
    {
        HttpHost proxy = new HttpHost("localhost", 1080);
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        httpclient = HttpClients.custom().setRoutePlanner(routePlanner).build();
    }
/*
传入url
拼装header
得到body
正则处理
获取ip端口号
 */
    @Test
    public void test() throws Exception {
        String url = "https://www.google.com";
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = httpclient.execute(get);
        System.out.println(response.getStatusLine().getStatusCode());
        response.close();
    }
}
