package com.bookmark.dao;

import com.bookmark.pojo.BookmarkTree;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface BookmarkTreeDao {

    /**
     * Description: 1.针对插件端的输入, 更新书签树信息(同步) //TODO
     *              2.保存他人公开书签夹到自己书签树(在service层将children更新)
     * @param ID: 书签夹id
     * @param children: 书签夹更新后的children信息
     * @return:  操作成功 ? 1 : 0
     */
    @Update("update book set children=#{children} where ID=#{ID}")
    int updateBookmarkTreeByID(Integer ID, String children);
    
    /**
      * Description: 1.针对插件端和前端, 获取书签树数据(恢复)
      *              2.获取某个用户书签夹下的数据
      * @param ID: 书签夹id
      * @return:  BookmarkTree对象，可在服务层转为json格式
      */
    @Select("select * from book where ID=#{ID}")
    BookmarkTree getBookmarkTreeByID(Integer ID);

    /**
      * Description: 返回所有用户公开并且是根结点(parentID=0)的书签(夹)信息
      * @param
      * @return: List<BookmarkTree>
      */
    @Select("select * from book where isPublic=1 and parentID=0")
    List<BookmarkTree> getPublicBookmarks();

    @Select("select ID from book where parentID=#{parentID}")
    List<Integer> getAllChildID(Integer parentID);

    @Select("select * from book where parentID=#{parentID}")
    List<BookmarkTree> getAllChild(Integer parentID);

    @Select("select rootID from user where uid=#{uid}")
    int getBookmarkTreeIDByUid(Integer uid);

    /**
      * Description: 获得当前书签夹(ID)所属的用户id
      * @param ID: 当前书签夹的ID
      * @return:  所属用户ID
      */
    @Select("select uid from book where ID=#{ID}")
    int getBookmarkTreeUidByID(Integer ID);

    /**
      * Description: 修改书签(夹)名称
      * @param ID: 书签夹id
      * @param uid: 书签树所属用户的id
      * @return: 操作成功 ? 1 : 0
      */
    @Update("update book set title=#{newTitle} where ID=#{ID} and uid=#{uid}")
    int updateBookmarkName(Integer ID, Integer uid, String newTitle);

    /**
      * Description: 公开(取消公开)书签夹
      * @param ID: 书签夹id
      * @param uid: 书签树所属用户的id
      * @return: 操作成功 ? 1 : 0
      */
    @Update("update book set isPublic=#{isPublic} where ID=#{ID} and uid=#{uid}")
    int updateBookmarkIsPublic(Integer ID, Integer uid, Integer isPublic);

    /**
      * Description: 删除书签(夹)
      * @param ID: 书签夹id
      * @param uid: 书签树所属用户的id
      * @return: 操作成功 ? 1 : 0
      */
    @Delete("delete from book where ID=#{ID} and uid=#{uid}")
    int deleteBookmarkByID(Integer ID, Integer uid);

}
