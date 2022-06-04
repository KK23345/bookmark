package com.bookmark.dao;

import com.bookmark.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {

    @Select("select * from user where u_id=#{uId}")
    User getByUId(Integer uId);

    @Select("select * from user where u_name=#{uName}")
    User getByUName(String uName);

    @Insert("insert into user (u_name, u_password) values (#{uName}, #{uPassword})")
    int addUser(User user);

    @Update("update user set u_name=#{uName}, u_password=#{uPassword} where u_id=#{uId}")
    int updateUser(User user);

    @Delete("delete from user where u_id=#{uId}")
    int deleteByUId(Integer uId);

}
