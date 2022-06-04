package com.bookmark;

import com.bookmark.dao.UserDao;
import com.bookmark.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class DaoTest {
    @Resource
    private UserDao userDao;

    @Test
    void contextLoads() {
        System.out.println(userDao.getByUId(1));
        System.out.println(userDao.getByUName("zhangsan"));

        //User user = new User();
        //user.setUName("kk");
        //user.setUPassword("1111111");
        //System.out.println(userDao.addUser(user));

        //user.setUPassword("123456");
        //System.out.println(userDao.updateUser(user));

        //System.out.println(userDao.deleteByUId(user.getUId()));
    }


}
