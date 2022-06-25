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

    // url:47.96.41.120:10030/login?name=kk&password=123456
    @GetMapping(value = "/login")
    public @ResponseBody Object login(@RequestParam HashMap<String, String> data) {
        User user = new User();
        user.setName(data.get("name"));
        user.setPassword(data.get("password"));
        int result=userService.login(user);
        if(result==1)
        {
            int uid=userService.getUID(user);
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

    // url:47.96.41.120:10030/login?name= &password=
    @GetMapping(value = "/register")
    public @ResponseBody Object register(@RequestParam HashMap<String, String> data) {
        User user = new User();
        user.setName(data.get("name"));
        user.setPassword(data.get("password"));
        int result= userService.register(user);
        Map<String,String> res = new HashMap<>();
        if(result==1)
        {
            int uid=userService.getUID(user);
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
