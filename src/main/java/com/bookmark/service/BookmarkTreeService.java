package com.bookmark.service;

import com.bookmark.dao.BookmarkTreeDao;
import com.bookmark.dao.UserDao;
import com.bookmark.pojo.BookmarkTree;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BookmarkTreeService {

    @Resource
    private BookmarkTreeDao btDao;

    @Resource
    private UserDao userDao;

    //======================前端的接口==============================

    /**
      * Description: 重命名书签夹
      * @param uid: 书签夹所属用户id
	  * @param btID: 书签夹ID
	  * @param newName: 新名称
      * @return: 1 : 重命名成功 ; -1 : 权限不够(其他人不能更改自己的书签夹名) ; -2 : 更新名称失败
      */
    public Integer renameBT(Integer uid, Integer btID, String newName) {
        Integer btUid = btDao.getBookmarkTreeUidByID(btID);
        if(!uid.equals(btUid)) return -1;

        return (btDao.updateBookmarkName(btID, uid, newName) == 1)
                ? 1
                : -2;
    }
    
    /**
      * Description: 公开书签夹
      * @param uid: 书签夹所属用户id
	  * @param btID: 书签夹ID
      * @return:  1 : 公开成功 ; -1 : 权限不够(其他人不能公开自己的书签夹) ; -2 : 公开失败
      */
    public Integer publicBT(Integer uid, Integer btID) {
        Integer btUid = btDao.getBookmarkTreeUidByID(btID);
        if(!uid.equals(btUid)) return -1;
        
        return (btDao.updateBookmarkIsPublic(btID, uid) == 1)
                ? 1
                : -2;
    }

    /**
      * Description: 删除书签(夹)
      * @param uid: 书签(夹)所属用户id
	  * @param btID: 书签(夹)ID
      * @return:  1 : 删除成功 ; -1 : 权限不够(其他人不能删除自己的书签(夹)) ; -2 : 删除失败
      */
    public Integer deleteBT(Integer uid, Integer btID) {
        BookmarkTree bt = btDao.getBookmarkTreeByID(btID); //当前要删除的书签(夹)
        if(bt == null) return -2;

        Integer btUid = bt.getUid();
        if(!uid.equals(btUid)) return -1;

        if(btDao.deleteBookmarkByID(btID, uid) == 1) {
            if(bt.getParentID() > 0) { //当前书签夹还有父书签夹，需要更新父书签夹的children信息
                Integer parentID = bt.getParentID();
                BookmarkTree parentBT = btDao.getBookmarkTreeByID(parentID);
                String children = parentBT.getChildren();// children: "1,2,3,"
                children = children.replace(btID+",", "");
                btDao.updateBookmarkTreeByID(parentID, children);
            }
            //如果要删除的是书签夹，需要把所有子节点都删除
            if(bt.getType()==0) deleteAllChild(btID,uid);
            return 1;
        } else {
            return -2;
        }
    }

    //TODO 复制书签夹
    public Integer copyBT(Integer pid, Integer btID, Integer uid) {
        BookmarkTree bt = btDao.getBookmarkTreeByID(btID); //要复制的书签(夹)
        if(pid.equals(bt.getID())) {
            if(bt.getIsPublic() == 1) {//书签夹是公开的
                //强复制, 重新更改ID和uid, 并加入到数据库中
                int maxID = userDao.getMaxBookID();
                bt.setID(maxID + 1);
                bt.setUid(uid);
                int parentID = btDao.getBookmarkTreeIDByUid(uid);
                bt.setParentID(parentID);
                userDao.addBook(bt);
                //修改当前用户(uid)的书签夹(rootID)对应的children信息
                BookmarkTree parentBT = btDao.getBookmarkTreeByID(parentID);
                String children = parentBT.getChildren();
                children = new StringBuilder(children).append(bt.getID()).append(",").toString();
                btDao.updateBookmarkTreeByID(parentID, children);
            } else {
                return -1;
            }
        }
        return -2;
    }
    private void deleteAllChild(Integer pid,Integer uid){
        for(int childId:btDao.getAllChildID(pid)){
            int type=btDao.getBookmarkTreeByID(childId).getType();
            if(type==0){
                btDao.deleteBookmarkByID(childId, uid);
                deleteAllChild(childId,uid);
            }
            else{
                btDao.deleteBookmarkByID(childId, uid);
            }
        }

    }
    //======================插件端的接口==============================

    /**
      * Description: 插件端上传书签树(同步)
      * @param bt: 书签树
      * @return:  上传成功 ? 1 : -1
      */
    public Integer uploadBT(BookmarkTree bt) {
        //TODO 处理json格式书签树

        return 0;
    }

    /**
      * Description: 根据uid查找书签树rootID, 并将结果返回给插件端
      * @param uid: 用户id
      * @param ID: 书签树ID
      * @return: 书签树对象
      */
    public BookmarkTree obtainBT(Integer uid, Integer ID) {
        BookmarkTree res;
        res = btDao.getBookmarkTreeByID(ID);
        if(res != null && res.getIsPublic() == 1) { //是公开的书签夹
            //TODO 转为json格式
        }
        return res;
    }


}
