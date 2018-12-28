package com.zt10.proxyPool.pool;

import com.zt10.proxyPool.exception.BadHttpException;
import com.zt10.proxyPool.utils.NetUtil;
import com.zt10.proxyPool.utils.ProxyWebsite;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Getter {

    public List getProxys() {
        List result = new ArrayList(40);
        for (Method method : Getter.class.getMethods()) {
            ProxyWebsite proxyWebsite = method.getAnnotation(ProxyWebsite.class);
            try {
                if (proxyWebsite != null) {
                    List temp = (List) method.invoke(new Getter());
                    result.addAll(temp);
                }
            } catch (Exception e) {
                throw new BadHttpException("");
            }
        }
        return result;
    }

    private final Pattern freePattern = Pattern.compile("data-title=\"IP\">(.*?)</td>.*?\"PORT\">(.*?)</td>", Pattern.DOTALL);

    @ProxyWebsite
    public List freeProxy() {
        String url = "https://www.kuaidaili.com/free/inha/";
        String content = NetUtil.getInsideOfWallContent(url);
        Matcher m = freePattern.matcher(content);
        ArrayList result = new ArrayList(20);
        while (m.find()) {
            result.add(m.group(1) + ":" + m.group(2));
        }
        return result;
    }

    private final Pattern xicidailiPattern = Pattern.compile("<td class=\"country\"><img src=\"//fs.xicidaili.com/images/flag/cn.png\" alt=\"Cn\" /></td>\\s*<td>(.*?)</td>\\s*<td>(.*?)</td>", Pattern.DOTALL);

    @ProxyWebsite
    public List xicidailiProxy() {
        String url = "https://www.xicidaili.com/wt/1";
        String content = NetUtil.getInsideOfWallContent(url);
        Matcher m = xicidailiPattern.matcher(content);
        ArrayList result = new ArrayList(20);
        while (m.find()) {
            result.add(m.group(1) + ":" + m.group(2));
        }
        return result;
    }

    private final Pattern wuyouPattern = Pattern.compile("<ul class=\"l2\">\\s*<span><li>(.*?)</li></span>.*?port.*?\">(.*?)</li></span>");

    @ProxyWebsite
    public List wuyouProxy() {
        String url = "http://www.data5u.com/";
        String content = NetUtil.getInsideOfWallContent(url);
        Matcher m = wuyouPattern.matcher(content);
        ArrayList result = new ArrayList(20);
        while (m.find()) {
            result.add(m.group(1) + ":" + m.group(2));
        }
        return result;
    }
}
