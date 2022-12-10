package com.paymybuddy.paymybuddy.controller;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomePageController {

    @RequestMapping(value = "/")
    public String defaultPage(){
        return "homePage";
    }

    @RequestMapping(value = "/homePage")
    public String homePage (){
        return "homePage";
    }

    @RequestMapping(value = "/TESTcontactPAGE")
    public String test(){
        return "TESTcontactPAGE";
    }

    @GetMapping(value = "/adminPage")
    public String adminPage(){
        return "/adminPAge";
    }




}
