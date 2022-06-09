package com.bookmark.dao;

<<<<<<< HEAD
import com.bookmark.pojo.BookmarkTree;
=======
import com.bookmark.pojo.Book;
>>>>>>> df7c5de030d7d264d4f3b8ce86e50cb5bd1111d0
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

    @Insert("insert into book(ID,parentID,type,uid,title,url,children,favorites) values(#{ID},#{parentID},#{type},#{uid},#{title},#{url},#{children},#{favorites});")
<<<<<<< HEAD
    int addBook(BookmarkTree bookmarkTree);
=======
    int addBook(Book book);
>>>>>>> df7c5de030d7d264d4f3b8ce86e50cb5bd1111d0

    @Select ("select coalesce(max(ID),0) from bookmark.book")
    int getMaxBookID();

    @Update("update user set name=#{name}, password=#{password}, rootID=#{rootID} where uid=#{uid}")
    int updateUser(User user);

    @Delete("delete from user where uid=#{uid}")
    int deleteByUId(Integer uid);

}
