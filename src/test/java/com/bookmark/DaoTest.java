package com.bookmark;

import com.bookmark.dao.BookmarkTreeDao;
import com.bookmark.dao.UserDao;
import com.bookmark.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class DaoTest {
    @Resource
    private UserDao userDao;

    @Resource
    private BookmarkTreeDao btDao;

    @Test
    void bookmarkTreeDaoTest() {
        System.out.println(btDao.getPublicBookmarks());
        System.out.println(btDao.getBookmarkTreeByID(1, 1));
    }


    @Test
    void userDaoTest() {
        System.out.println(userDao.getByUId(2));
        System.out.println(userDao.getByUName("zhangsan"));

//        User user = new User();
//        user.setName("kk");
//        user.setPassword("1111111");
//        user.setRootID(2);
//        System.out.println(userDao.addUser(user));
//
//        user = userDao.getByUName("kk");
//        user.setPassword("123456");
//        System.out.println(user);

    }

}
