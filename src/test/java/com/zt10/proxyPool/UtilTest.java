package com.zt10.proxyPool;

import com.zt10.proxyPool.pool.Getter;
import com.zt10.proxyPool.pool.schedule.Tester;
import com.zt10.proxyPool.utils.NetUtil;
import com.zt10.proxyPool.utils.ProxyWebsite;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilTest {

    private String regex_string = "class=\"sorting_1\">(.*?)</td>\\W+<td>(.*?)</td>";

    private final Pattern wuyouPattern = Pattern.compile(regex_string);

    //@Test
    public void test() {
        String url = "https://www.xroxy.com/free-proxy-lists/?country=CN";
        String content = NetUtil.getOutsideOfWallContent(url);
        System.out.println(content);
        Matcher m = wuyouPattern.matcher(content);
        ArrayList result = new ArrayList(20);
        int index = 0;
        while (m.find()) {
            System.out.println(index++ + "  ---  " + m.group(1) + ":" + m.group(2));
        }
    }

    //@Test
    public void test1() {
        //NetUtil.get("https://www.google.com", "47.94.88.230", "1080");
        System.out.println(NetUtil.getOutsideOfWallContent("https://www.google.com"));
        //NetUtil.getOutsideOfWallContent("http://free-proxy.cz/zh/proxylist/country/all/all/ping/all");
        //System.out.println(NetUtil.getOutsideOfWallContent("https://www.xroxy.com/free-proxy-lists/?country=CN"));
    }

    //@Test
    public void testTemp() {
        List list = new ArrayList();
        List list2 = new ArrayList();
        list.add("1");
        list.add("2");
        list2.add("3");
        list2.add("4");
        list.addAll(list2);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + " ---- " + list.get(i));
        }
    }

}
