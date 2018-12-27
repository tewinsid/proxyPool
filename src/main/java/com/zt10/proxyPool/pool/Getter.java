package com.zt10.proxyPool.pool;

import com.zt10.proxyPool.utils.NetUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Getter {

    public List getProxys() {

        return new ArrayList();
    }

    public List freeProxy() {
        String url = "https://www.kuaidaili.com/free/inha/";
        String content = NetUtil.getInsideOfWallContent(url);
        Pattern p = Pattern.compile("data-title=\"IP\">(.*?)</td>.*?\"PORT\">(.*?)</td>", Pattern.DOTALL);
        Matcher m = p.matcher(content);
        ArrayList result = new ArrayList(20);
        while (m.find()) {
            result.add(m.group(1) + ":" + m.group(2));
        }
        return result;
    }
}
