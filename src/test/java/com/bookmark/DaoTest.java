package com.bookmark;

import com.bookmark.dao.BookmarkTreeDao;
import com.bookmark.dao.UserDao;
import com.bookmark.pojo.Book;
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
<<<<<<< HEAD
    void bookmarkTreeDaoTest() {
        System.out.println(btDao.getPublicBookmarks());
        System.out.println(btDao.getBookmarkTreeByID(1, 1));


=======
    void contextLoads() {
        Book book=new Book();
        book.setChildren("");
        book.setFavorites(0);
        book.setID(0);
        book.setParentID(-1);
        book.setUrl("");
        book.setUid(0);
        book.setType(0);
        book.setIsPublic(0);
        book.setTitle("");
        userDao.addBook(book);
        /**
         User user = new User();
         user.setName("kk");
         user.setPassword("1111111");
         user.setRootID(2);
         System.out.println(userDao.addUser(user));

         user = userDao.getByUName("kk");
         user.setPassword("123456");
         System.out.println(user);
         ***/
        //System.out.println(userDao.updateUser(user));

//        System.out.println(userDao.deleteByUId(user.getUid()));
>>>>>>> df7c5de030d7d264d4f3b8ce86e50cb5bd1111d0
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
