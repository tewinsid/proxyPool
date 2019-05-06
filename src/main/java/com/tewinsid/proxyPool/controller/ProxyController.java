package com.tewinsid.proxyPool.controller;

import com.tewinsid.proxyPool.service.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ProxyController {

    @Autowired
    private ProxyService proxyService;

    @RequestMapping("/")
    public String index() {
        return "小纲代理程池，公开无密，请勿压测。";
    }

    @RequestMapping("/get")
    public List getProxy(HttpServletRequest request) {
        String number = request.getParameter("num");
        if (number == null || number == "") {
            number = "1";
        }
        System.out.println(number);
        return proxyService.getProxys(Integer.valueOf(number));
    }
}
