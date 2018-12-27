package com.zt10.proxyPool.utils;

import com.zt10.proxyPool.exception.BadHttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NetUtil {
    public static Boolean get(String url, String ip, String port) {
        HttpResponse response = getResponse(ip, Integer.valueOf(port), url);
        int status_code = response.getStatusLine().getStatusCode();
        if (status_code == 200) {
            return true;
        }
        return false;
    }

    private static HttpResponse getResponse(String ip, int port, String url) {
        HttpHost proxy = new HttpHost(ip, port);
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        CloseableHttpClient httpclient = HttpClients.custom().setRoutePlanner(routePlanner).build();

        HttpGet get = new HttpGet(url);

        get.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36");
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(get);
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
        return response;
    }


    public static String getOutsideOfWallContent(String url) {
        HttpResponse response = getResponse("54.39.138.151", 3128, url);
        String result = "";
        String temp = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            while ((temp = reader.readLine()) != null) {
                result += temp;
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadHttpException("getOutsideOfWallContent exception");
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new BadHttpException("getOutsideOfWallContent reader close");
            }
        }
    }

    public static String getInsideOfWallContent(String url) {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        String result = "";
        String temp = "";
        BufferedReader reader = null;
        try {
            HttpResponse response = httpClient.execute(get);
            reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            while ((temp = reader.readLine()) != null) {
                result += temp;
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadHttpException("getOutsideOfWallContent exception");
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new BadHttpException("getOutsideOfWallContent reader close");
            }
        }
    }

}
