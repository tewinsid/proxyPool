package com.zt10.proxyPool.utils;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLContext;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

public class SocksSSLConnectionSocketFactory extends SSLConnectionSocketFactory {


    public SocksSSLConnectionSocketFactory(SSLContext sslContext) {
        super(sslContext);
    }

    @Override
    public Socket createSocket(HttpContext context) {
//        InetSocketAddress socksaddr = (InetSocketAddress) context.getAttribute("socks.address");
        InetSocketAddress socksaddr = new InetSocketAddress("127.0.0.1", 1080);
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, socksaddr);
        return new Socket(proxy);
    }

}
