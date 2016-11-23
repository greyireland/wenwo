package com.finalx.controller;

import com.finalx.service.WendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tengyu on 2016/7/13.
 */
@Controller
public class SettingController {

    @Autowired
    WendaService wendaService;

    @RequestMapping(path = "/setting")
    @ResponseBody
    public String setting(){
        return "Setting ok" + wendaService.getMessage(32);
    }
}