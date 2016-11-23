package com.finalx.controller;

import com.finalx.service.WendaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by tengyu on 2016/7/13.
 */
@Controller
public class TestController {
    @Autowired
    WendaService wendaService;

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    /*@RequestMapping(path = {"/", "/index"}, method = RequestMethod.GET)
    @ResponseBody
    public String index(HttpSession httpSession) {

        *//*if (httpSession != null) {
            return "hello,world"+httpSession.getAttribute("msg");
        }*//*
        logger.info("welcome to index.");
        return "index"+wendaService.getMessage(110);
    }*/

    @RequestMapping(path = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile2(@PathVariable("userId") int userId,
                          @PathVariable("groupId") int groupId,
                           @RequestParam(value = "type",defaultValue = "321",required = false) int type) {
        return String.format("Profile Page of %d:%d,type:%d",groupId, userId,type);
    }

    @RequestMapping(path = {"/vm"}, method = {RequestMethod.GET})
    public String template(Model model) {
        model.addAttribute("value1", "value11");
        model.addAttribute("value2", "value12");
        model.addAttribute("", "");
        List<String> colors = Arrays.asList("green", "red");
        model.addAttribute("colors", colors);
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < 4; i++) {
            map.put(String.valueOf(i),String.valueOf(i * i));
        }
        model.addAttribute("map", map);
        //model.addAttribute("user", new User("Tom"));
        return "home";
    }

    @RequestMapping(path = {"/request"},method = RequestMethod.GET)
    @ResponseBody
    public String profile2(Model model, HttpServletRequest request,
                           HttpServletResponse response,
                           HttpSession session) {
        StringBuilder sb=new StringBuilder();
        Enumeration<String> headerNames=request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name=headerNames.nextElement();
            sb.append(name + ":" + request.getHeader(name) + "<br>");
        }
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                sb.append("cookie:name:" + cookie.getName()+",value:"+cookie.getValue()+"<br>");
            }
        }
        sb.append(request.getMethod() + "<br>");
        sb.append(request.getQueryString() + "<br>");
        sb.append(request.getPathInfo() + "<br>");
        sb.append(request.getRequestURL() + "<br>");
        model.addAttribute("sb", sb);

        response.addHeader("name:","final");
        response.addCookie(new Cookie("username","xiaownag"));
        //response.getOutputStream().write();

        return sb.toString();
    }

    @RequestMapping(path = {"/redirect/{code}"})
    public RedirectView redirect(@PathVariable("code") int code,
                                 HttpSession httpSession) {
        httpSession.setAttribute("msg", "jump from redirect");
        RedirectView red = new RedirectView("/",true);
        if (code == 301) {
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
    }

    @RequestMapping(path = "/admin")
    @ResponseBody
    public String admin(@RequestParam("key") String key) {
        if ("admin".equals(key) ) {
            return "hello,admin";
        }
        throw new IllegalArgumentException("参数不对");
    }
    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e) {
        return "error:" + e.getMessage();

    }

}
