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
//        System.out.println(userDao.getByUId(2));
//        System.out.println(userDao.getByUName("zhangsan"));

        User user = new User();
        user.setName("kk");
        user.setPassword("1111111");
        user.setRootID(2);
        System.out.println(userDao.addUser(user));

        user = userDao.getByUName("kk");
        user.setPassword("123456");
        System.out.println(user);
        //System.out.println(userDao.updateUser(user));

//        System.out.println(userDao.deleteByUId(user.getUid()));
    }


}
