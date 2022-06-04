package com.bookmark.service;

import com.bookmark.dao.UserDao;
import com.bookmark.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserDao userDao;

    public Boolean login(User user) {
        User u = userDao.getByUName(user.getUName()); //从数据库中查询当前user用户
        return u != null && u.getUPassword().equals(user.getUPassword());
    }

    public Boolean register(User user) {
        User u = userDao.getByUName(user.getUName());
        if(u == null) { //不存在名称相同的用户
            userDao.addUser(user);
            return true;
        } else {
            return false;
        }
    }
}
