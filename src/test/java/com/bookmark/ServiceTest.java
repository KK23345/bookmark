package com.bookmark;

<<<<<<< HEAD
import com.bookmark.pojo.User;
import com.bookmark.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
=======
import com.bookmark.dao.UserDao;
import com.bookmark.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.bookmark.pojo.User;
>>>>>>> df7c5de030d7d264d4f3b8ce86e50cb5bd1111d0

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
