package com.zt10.proxyPool.utils;

import com.zt10.proxyPool.exception.BadHttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NoRouteToHostException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class NetUtil {
    public static Boolean get(String url, String ip, String port) {
        HttpResponse response = null;
        try {
            response = getResponse(ip, Integer.valueOf(port), url);
        } catch (IOException e) {
            return false;
        }
        if (response == null) {
            return false;
        }
        int status_code = response.getStatusLine().getStatusCode();
        if (status_code == 200) {
            return true;
        }
        return false;
    }

    private static HttpResponse getResponse(String ip, int port, String url) throws IOException {
        HttpHost proxy = new HttpHost(ip, port);
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        CloseableHttpClient httpclient = HttpClients.custom().setRoutePlanner(routePlanner).build();

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .build();

        HttpGet get = new HttpGet(url);

        get.setConfig(requestConfig);

        get.setHeader("User-Agent", "Mozilla/5.0 (iPad; CPU OS 11_0 like Mac OS X) AppleWebKit/604.1.34 (KHTML, like Gecko) Version/11.0 Mobile/15A5341f Safari/604.1");
        CloseableHttpResponse response = null;
        return httpclient.execute(get);
    }


    public static String getOutsideOfWallContent(String url) {
        String result = "";
        String temp = "";
        BufferedReader reader = null;
        try {
            HttpResponse response = getResponse("127.0.0.1", 1080, url);
            if (response == null) {
                return "response is null";
            }
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
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new BadHttpException("getOutsideOfWallContent reader close");
            }
        }
    }

    public static String getInsideOfWallContent(String url) {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        get.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36");
        get.setHeader("Cookie", "yd_cookie=85db9b58-15ea-4ff205f11ee1907f81fc26d28fc70c2fcef4; _ydclearance=1c852b147583b676b31d9f07-8bb2-4949-969f-5e943d3b2f3e-1545969219; Hm_lvt_1761fabf3c988e7f04bec51acd4073f4=1545717687,1545962028; Hm_lpvt_1761fabf3c988e7f04bec51acd4073f4=1545964520");
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
