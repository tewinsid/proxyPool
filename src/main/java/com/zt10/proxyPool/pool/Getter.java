package com.zt10.proxyPool.pool;

import com.zt10.proxyPool.exception.BadHttpException;
import com.zt10.proxyPool.utils.NetUtil;
import com.zt10.proxyPool.utils.ProxyWebsite;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Getter {

    //TODO 此处通过类变量进行页码传递，考虑通过参数形式传递进入方法，尽量减少副作用
    private int page_num = 1;

    public List getProxys() {
        List result = new ArrayList(100);
        for (Method method : Getter.class.getMethods()) {
            ProxyWebsite proxyWebsite = method.getAnnotation(ProxyWebsite.class);
            try {
                if (proxyWebsite != null) {
                    boolean condition = "0".equals(proxyWebsite.value()) ||
                            "1".equals(proxyWebsite.value()) && (page_num % 50 == 0 || page_num == 1);
                    if (condition) {

                        List temp = (List) method.invoke(this);
                        result.addAll(temp);
                    }
                }
            } catch (Exception e) {
                throw new BadHttpException("");
            }
        }
        page_num++;
        if (page_num > 500) {
            page_num = 0;
        }
        return result;
    }


    private final Pattern freePattern = Pattern.compile("data-title=\"IP\">(.*?)</td>.*?\"PORT\">(.*?)</td>", Pattern.DOTALL);

    @ProxyWebsite("0")
    public List freeProxy() {
        String url = "https://www.kuaidaili.com/free/inha/" + page_num;
        return new RegexTemplate().doProcess(freePattern, url, new InSidRegexProcessor());

    }

    private final Pattern xicidailiPattern = Pattern.compile("<td class=\"country\"><img src=\"//fs.xicidaili.com/images/flag/cn.png\" alt=\"Cn\" /></td>\\s*<td>(.*?)</td>\\s*<td>(.*?)</td>", Pattern.DOTALL);

    @ProxyWebsite("0")
    public List xicidailiProxy() {
        String url = "https://www.xicidaili.com/wt/" + page_num;
        return new RegexTemplate().doProcess(xicidailiPattern, url, new InSidRegexProcessor());

    }

    private final Pattern wuyouPattern = Pattern.compile("<ul class=\"l2\">\\s*<span><li>(.*?)</li></span>.*?port.*?\">(.*?)</li></span>");

    @ProxyWebsite("1")
    public List wuyouProxy() {
        String url = "http://www.data5u.com/";
        return new RegexTemplate().doProcess(wuyouPattern, url, new InSidRegexProcessor());

    }

    private final Pattern xroxyPattern = Pattern.compile("class=\"sorting_1\">(.*?)</td>\\W+<td>(.*?)</td>");

    @ProxyWebsite("1")
    public List xroxyProxy() {
        String url = "https://www.xroxy.com/free-proxy-lists/?country=CN";
        return new RegexTemplate().doProcess(xroxyPattern, url, new OutSidRegexProcessor());
    }

    private final Pattern sixsixPattern = Pattern.compile("((?:(?:25[0-5]|2[0-4]\\d|(?:1\\d{2}|[1-9]?\\d))\\.){3}(?:25[0-5]|2[0-4]\\d|(?:1\\d{2}|[1-9]?\\d)))</td><td>(.*?)</td>");
    @ProxyWebsite("0")
    public List sixsixProxy() {
        String url = "http://www.66ip.cn/" + page_num + ".html";
        return new RegexTemplate().doProcess(sixsixPattern, url, new InSidRegexProcessor());
    }

    //TODO 需要cookie
//    private final Pattern permPattern = Pattern.compile("");
//    @ProxyWebsite("1")
//    public List permProxy() {
//        String url = "http://www.66ip.cn/" + page_num + ".html";
//        return new RegexTemplate().doProcess(sixsixPattern, url, new OutSidRegexProcessor());
//    }


}

interface RegexProcess {
    String process(String url);
}

class RegexTemplate {
    public List doProcess(Pattern pattern, String url, RegexProcess processor) {
        String content = "";
        try {
            content = processor.process(url);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadHttpException(url);
        }
        Matcher m = pattern.matcher(content);
        ArrayList result = new ArrayList(20);
        while (m.find()) {
            result.add(m.group(1) + ":" + m.group(2));
        }
        return result;
    }
}

class OutSidRegexProcessor implements RegexProcess {
    @Override
    public String process(String url) {
        return NetUtil.getOutsideOfWallContent(url);
    }
}

class InSidRegexProcessor implements RegexProcess {
    @Override
    public String process(String url) {
        return NetUtil.getInsideOfWallContent(url);
    }
}
