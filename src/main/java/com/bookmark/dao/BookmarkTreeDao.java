package com.bookmark.dao;

import com.bookmark.pojo.BookmarkTree;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BookmarkTreeDao {

    /**
     * Description: 1.针对插件端的输入, 更新书签树信息(同步)
     *              2.保存他人公开书签夹到自己书签树(在service层将children更新)
     * @param bookmarkTree: service层将传来的json格式数据转为BookmarkTree对象
     * @return:  操作成功 ? 1 : 0
     */
    @Update("update book set children=#{children} where uid=#{uid}")
    int updateBookmarkTreeByUid(BookmarkTree bookmarkTree);
    
    /**
      * Description: 1.针对插件端和前端, 获取书签树数据(恢复)
      *              2.获取某个用户书签夹下的数据
      * @param ID: 书签夹id
	  * @param uid: 书签树所属用户的id
      * @return:  BookmarkTree对象，可在服务层转为json格式
      */
    @Select("select * from book where ID=#{ID} and uid=#{uid}")
    BookmarkTree getBookmarkTreeByID(Integer ID, Integer uid);

    /**
      * Description: 返回所有用户公开并且是根结点(parentID=0)的书签(夹)信息
      * @param
      * @return: List<BookmarkTree>
      */
    @Select("select * from book where isPublic=1 and parentID=0")
    List<BookmarkTree> getPublicBookmarks();

    /**
      * Description: 修改书签(夹)名称
      * @param bookmarkTree: service层封装好的修改后的书签(夹)
      * @return: 操作成功 ? 1 : 0
      */
    @Update("update book set title=#{title} where ID=#{ID}")
    int updateBookmarkName(BookmarkTree bookmarkTree);

    /**
      * Description: 公开(取消公开)书签夹
      * @param bookmarkTree: service层封装好的修改后的书签(夹)
      * @return: 操作成功 ? 1 : 0
      */
    @Update("update book set isPublic=#{isPublic} where ID=#{ID}")
    int updateBookmarkIsPublic(BookmarkTree bookmarkTree);

    /**
      * Description: 删除书签(夹)
      * @param bookmarkTree: service层封装好的要修改的书签(夹)
      * @return: 操作成功 ? 1 : 0
      */
    @Delete("delete from book where ID=#{ID}")
    int deleteBookmarkByID(BookmarkTree bookmarkTree);

}
