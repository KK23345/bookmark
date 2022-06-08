package com.bookmark;

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

    @Test
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
    }


}
