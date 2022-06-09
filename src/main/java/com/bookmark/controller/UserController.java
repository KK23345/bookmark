package com.bookmark.controller;

import com.bookmark.pojo.User;
import com.bookmark.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {

    @Resource
    private UserService userService;
    private Map<String,String> res = new HashMap<>();

    @GetMapping(value = "/login")
    public @ResponseBody Object login(User user) {

        int result=userService.login(user);
        if(result==1)
        {
            int uid=userService.getUid(user);
            res.put("msg","success");
            res.put("errorCode","1");
            res.put("httpCode","200");
            res.put("uid",Integer.toString(uid));
        }
        else if(result==-1) {
            res.put("msg","wrong password");
            res.put("errorCode","-1");
            res.put("httpCode","200");
        }
        else{
            res.put("msg","user not exist");
            res.put("errorCode","-2");
            res.put("httpCode","200");
        }
        return res;
    }

    @GetMapping(value = "/register")
    public @ResponseBody Object register(User user) {
        int result= userService.register(user);
        Map<String,String> res = new HashMap<>();
        if(result==1)
        {
            int uid=userService.getUid(user);
            res.put("msg","success");
            res.put("errorCode","1");
            res.put("httpCode","200");
            res.put("uid",Integer.toString(uid));
        }
        else if(result==-1) {
            res.put("msg","duplicated username");
            res.put("errorCode","-1");
            res.put("httpCode","200");
        }
        else{
            res.put("msg","wrong");
            res.put("errorCode","-2");
            res.put("httpCode","200");
        }
        return res;
    }

}
