package com.bookmark.dao;

import com.bookmark.pojo.BookmarkTree;
import com.bookmark.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {

    @Select("select * from user where uid=#{uid}")
    User getByUId(Integer uid);

    @Select("select * from user where name=#{name}")
    User getByUName(String name);

    @Select("select * from user where name=#{name} and password=#{password}")
    User verifyUser(String name, String password);

    @Insert("insert into user (uid, name, password, rootID) values (#{uid}, #{name}, #{password}, #{rootID})")
    int addUser(User user);

    @Insert("insert into book(ID,parentID,type,uid,title,url,children,favorites) values(#{ID},#{parentID},#{type},#{uid},#{title},#{url},#{children},#{favorites});")
    int addBook(BookmarkTree bookmarkTree);

    @Select ("select coalesce(max(ID),0) from bookmark.book")
    int getMaxBookID();

    @Select ("select coalesce(max(uid),0) from bookmark.user")
    int getMaxUID();

    @Select("select rootID from user where name=#{name}")
    int getRootIDByName(String name);

    @Select("select uid from user where name=#{name}")
    int getUIDByName(String name);

    @Update("update user set name=#{name}, password=#{password}, rootID=#{rootID} where uid=#{uid}")
    int updateUser(User user);

    @Delete("delete from user where uid=#{uid}")
    int deleteByUId(Integer uid);

}
