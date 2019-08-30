package com.weilai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("wl/")
public class IndexController {

    @RequestMapping("index")
    public String index() {

        System.out.println("asdkjfhkjasd");
        return "login";
    }

    @GetMapping("index1")
    public String index1() {

        System.out.println("asdkjfhkjasd");
        return "index";
    }

}
