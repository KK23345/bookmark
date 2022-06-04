package com.bookmark.controller;

import com.bookmark.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login() {

    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public void register() {

    }

}
