package com.bookmark;

import com.bookmark.pojo.User;
import com.bookmark.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ServiceTest {
    @Resource
    private UserService userService;

    @Test
    void contextLoads() {
        User user=new User();
        user.setName("wangwu1");
        user.setPassword("123456");
        int result=userService.register(user);
        System.out.println(result);
    }
}
