package com.bookmark.dao;

import com.bookmark.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {

    @Select("select * from user where uid=#{uid}")
    User getByUId(Integer uid);

    @Select("select * from user where name=#{name}")
    User getByUName(String name);

    @Insert("insert into user (name, password, rootID) values (#{name}, #{password}, #{rootID})")
    int addUser(User user);

    @Update("update user set name=#{name}, password=#{password}, rootID=#{rootID} where uid=#{uid}")
    int updateUser(User user);

    @Delete("delete from user where uid=#{uid}")
    int deleteByUId(Integer uid);

}
