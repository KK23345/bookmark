package com.bookmark;

import com.bookmark.dao.BookmarkTreeDao;
import com.bookmark.dao.UserDao;
import com.bookmark.pojo.BookmarkTree;
import com.bookmark.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class DaoTest {
    @Resource
    private UserDao userDao;

    @Resource
    private BookmarkTreeDao btDao;

    @Test
    void bookmarkTreeDaoTest() {
        List<BookmarkTree> list = btDao.getAllChild(8);
        for(BookmarkTree child : list)
            System.out.println(child);
        //System.out.println(btDao.getPublicBookmarks());
        //System.out.println(btDao.getBookmarkTreeByID(1));
    }


    @Test
    void userDaoTest() {
        System.out.println(userDao.getByUId(2));
        System.out.println(userDao.getByUName("zhangsan"));

        System.out.println(userDao.verifyUser("kk", "123"));

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
