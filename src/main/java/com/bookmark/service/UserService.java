package com.bookmark.service;

import com.bookmark.dao.UserDao;
import com.bookmark.pojo.Book;
import com.bookmark.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserDao userDao;
    /**
     *  服务层login接口
     *  输入：user
     *  输出：  1：用户名密码匹配正确      -1密码错误     -2用户名不存在
     * **/
    public Integer login(User user) {
        User u = userDao.getByUName(user.getName()); //从数据库中查询当前user用户
        if(u== null)  return -2;
        else if(u.getPassword().equals(user.getPassword())) return 1;
        else return -1;
        //return u != null && u.getPassword().equals(user.getPassword());
    }

    public Integer register(User user) {
        User u = userDao.getByUName(user.getName());
        try
        {
            if (u == null) { //不存在名称相同的用户
                int bookID= userDao.getMaxBookID();
                user.setRootID(bookID+1);
                userDao.addUser(user);
                int uid= userDao.getByUName(user.getName()).getUid();
                Book b=new Book(bookID+1,-1,0,uid,"","","",0,0);
                userDao.addBook(b);
                return 1;
            } else {
                return -1;
            }
        }
        catch(Exception e){
            return -2;
        }
    }


    public Integer getUid(User user){
        User u=userDao.getByUName(user.getName());
        if(u!=null)
            return u.getUid();
        else return -1;
    }


}
