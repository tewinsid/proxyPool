package com.zt10.proxyPool.utils;

import com.zt10.proxyPool.exception.BadHttpException;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NetUtil {
    public static Boolean get(String url, String ip, String port){
        HttpHost proxy = new HttpHost(ip, Integer.valueOf(port));
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        CloseableHttpClient httpclient = HttpClients.custom().setRoutePlanner(routePlanner).build();

        HttpGet get = new HttpGet(url);

        get.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36");
        String temp = "";
        int status_code;
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(get);
            status_code = response.getStatusLine().getStatusCode();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadHttpException("bad get");
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new BadHttpException("bad get response close");
            }
        }

        if (status_code == 200) {
            return true;
        }
        return false;
    }
}
