package com.tewinsid.proxyPool;

import com.tewinsid.proxyPool.util.NetUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UtilTest {

    private String regex_string = "((?:(?:25[0-5]|2[0-4]\\d|(?:1\\d{2}|[1-9]?\\d))\\.){3}(?:25[0-5]|2[0-4]\\d|" +
            "(?:1\\d{2}|[1-9]?\\d)))</td><td>(.*?)</td>";

    private final Pattern myPattern = Pattern.compile(regex_string);

    //    @Test
    public void test() {
        String url = "http://www.66ip.cn/2.html";
        String content = NetUtil.getOutsideOfWallContent(url);
        System.out.println(content);
//        Matcher m = myPattern.matcher(content);
//        ArrayList result = new ArrayList(20);
//        int index = 0;
//        while (m.find()) {
//            System.out.println(index++ + "  ---  " + m.group(1) + ":" + m.group(2));
//        }
    }

    //    @Test
    public void test1() {
        //NetUtil.get("https://www.google.com", "47.94.88.230", "1080");
        System.out.println(NetUtil.getInsideOfWallContent("https://www.google.com"));
//        System.out.println(NetUtil.getOutsideOfWallContent("https://www.xroxy.com/free-proxy-lists/?country=CN"));
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
