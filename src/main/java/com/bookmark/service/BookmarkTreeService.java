package com.bookmark.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bookmark.dao.BookmarkTreeDao;
import com.bookmark.dao.UserDao;
import com.bookmark.pojo.BookmarkTree;
import com.bookmark.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class BookmarkTreeService {

    private static BookmarkTreeDao btDao;
    @Autowired
    public BookmarkTreeService(BookmarkTreeDao btDao) { //btDao会爆红()，需要再Mapper上加@Component注解
        BookmarkTreeService.btDao = btDao;
    }

    @Resource
    private UserDao userDao;

    //======================前端的接口==============================

    /**
      * Description: 重命名书签夹
      * @param uid: 书签夹所属用户id
	  * @param btID: 书签夹ID
	  * @param newName: 新名称
      * @return: > 0 : 重命名成功 ; -1 : 权限不够(其他人不能更改自己的书签夹名) ; -2 : sql更新名称失败
      */
    public Integer renameBT(Integer uid, Integer btID, String newName) {
        int btUid;
        try {
            btUid = btDao.getBookmarkTreeUidByID(btID);
        } catch (Exception e) { //btID不存在，公开失败
            return -2;
        }

        if(!uid.equals(btUid)) return -1;

        return (btDao.updateBookmarkName(btID, uid, newName) == 1)
                ? 1
                : -2;
    }


    /**
      * Description: 公开/私密书签夹(子书签(夹)也需要全部一致)
      * @param bt: 要操作的书签夹
      * @param isPublic: isPublic == 0 ? 私密 : 公开(1)
      * @return:  1 : 操作成功 ; -1 : 权限不够(其他人不能公开自己的书签夹) ; -2 : 公开失败
      */
    public Integer publicBTOrNot(BookmarkTree bt, Integer isPublic) {
        Integer uid = bt.getUid(); //书签夹所属用户id
        Integer btID = bt.getID(); //书签夹id

        int btUid; // 要公开/私密的书签夹所属用户的id
        try {
            btUid = btDao.getBookmarkTreeUidByID(btID);
        } catch (Exception e) { //btID不存在，公开失败
            return -2;
        }
        if(btUid != uid) return -1; //btID所属的用户与参数uid不相等，没有权限公开

        int res = btDao.updateBookmarkIsPublic(btID, uid, isPublic); //现公开当前书签夹
        if(bt.getType() == 0) { //bt是父书签夹，递归公开所有子书签(夹)
            res = doIsPublic(btID, uid, isPublic);
        }
        
        return (res == 1) ? 1 : -2;
    }

    private static Integer doIsPublic(Integer btID, Integer uid, Integer isPublic) {
        for(int childID : btDao.getAllChildID(btID)) {
            int res = btDao.updateBookmarkIsPublic(childID, uid, isPublic);
            if(res == 0) return -1; //更新失败
            BookmarkTree childBt = btDao.getBookmarkTreeByID(childID);
            if(childBt.getType() == 0) {
                doIsPublic(childID, uid, isPublic);
            }
        }
        return 1;
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

        User user = userDao.getByUId(uid);
        if(user.getRootID().equals(btID)) {//要删除的是根结点书签夹, 需要更新user表的rootID属性为0
            user.setRootID(0);
            userDao.updateUser(user);
        }

        if(btDao.deleteBookmarkByID(btID, uid) == 1) {
            if(bt.getParentID() > 0) { //当前书签夹还有父书签夹，需要更新父书签夹的children信息
                Integer parentID = bt.getParentID();
                BookmarkTree parentBT = btDao.getBookmarkTreeByID(parentID);
                String children = parentBT.getChildren(); // children: "1,2,3,"
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

    private void deleteAllChild(Integer pid,Integer uid){
        for(int childId : btDao.getAllChildID(pid)){
            int type = btDao.getBookmarkTreeByID(childId).getType();
            if(type == 0){
                btDao.deleteBookmarkByID(childId, uid);
                deleteAllChild(childId, uid);
            } else{
                btDao.deleteBookmarkByID(childId, uid);
            }
        }
    }


    /**
      * Description: 为uid用户新建一个书签夹
      * @param uid: 用户id
      * @return: 1 : 创建成功; -1 : 创建失败
      */
    public Integer createBT(Integer uid, Integer parentId, String btName) {
        User user = userDao.getByUId(uid);
        if(user == null) return -1;

        int maxBtID = userDao.getMaxBookID();
        int res = 1;
        BookmarkTree newBT;
        if(parentId == 0) { //当前用户没有父文件夹, 即user的rootID属性为0
            user.setRootID(maxBtID + 1);
            userDao.updateUser(user);
            newBT = new BookmarkTree(maxBtID+1, user.getRootID(), 0, uid, btName, "", "", 0, 0);
        } else { //有父文件夹, 需要更新父文件夹的children信息
            BookmarkTree parent = btDao.getBookmarkTreeByID(parentId);
            parent.setChildren(parent.getChildren() + (maxBtID + 1) + ",");
            res = btDao.updateBookmarkTreeByID(parent.getID(), parent.getChildren());
            if(res == 0) return -1;
            newBT = new BookmarkTree(maxBtID+1, parentId, 0, uid, btName, "", "", 0, 0);
        }
        res = userDao.addBook(newBT);

        return (res == 1) ? 1 : -1;
    }


    private static List<BookmarkTree> copyList; //保存强复制过程中新建的对象
    private static int btIDMax;    //book表中最大的ID序号
    /**
      * Description: 复制其他用户的书签夹到自己的根书签夹下(即user.rootID)
      * @param pid:  其他用户的id
	  * @param btID: 其他用户的书签(夹)id
	  * @param uid:  当前用户的id
      * @return: 1 : 复制成功 ; -1 : 其他用户未公开要复制的书签夹 ; -2 : 复制失败
      */
    public Integer copyBT(Integer pid, Integer btID, Integer uid) {
        BookmarkTree pRoot = btDao.getBookmarkTreeByID(btID);      //要复制的书签夹的根结点对象
        if(pRoot == null || !pRoot.getUid().equals(pid) || pRoot.getChildren() == null)
            return -2; //要复制的书签夹不存在, 或uid属性与pid不相符, 或要复制的书签夹的children为空(没有子书签(夹))

        copyList = new LinkedList<>();

        if(pRoot.getIsPublic() == 1) {  //书签夹是公开的
            //强复制, 需要新建对象，并修改属性: <ID, parentID> (相对应,放到map里), uid, children(根据前面的map)
            User user = userDao.getByUId(uid); //当前用户
            Integer rootID = user.getRootID(); //当前用户的根书签夹ID
            BookmarkTree uRoot = btDao.getBookmarkTreeByID(rootID); //要将pRoot复制到这个书签夹下

            btIDMax = userDao.getMaxBookID();
            btIDMax++; //此时btIDMax是newRoot的ID

            uRoot.setChildren(uRoot.getChildren() + btIDMax + ","); //更新uRoot的children信息
            int res = btDao.updateBookmarkTreeByID(rootID, uRoot.getChildren());
            if(res == 0) return -2; //更新失败

            BookmarkTree newRoot = new BookmarkTree(pRoot);
            //重新设置属性
            newRoot.setID(btIDMax);
            newRoot.setParentID(rootID);
            newRoot.setUid(uid);
            copyList.add(newRoot); //目前children和pRoot的一样

            doCopyBT(pRoot.getID(), newRoot, uid); //开始复制pRoot的children信息(所有的子书签(夹))到newRoot下

            //将复制好的所有对象加入数据库
            System.out.println("copyList size: " + copyList.size());
            for (BookmarkTree bt : copyList) {
                System.out.println(bt);
                res = userDao.addBook(bt);
                if(res == 0) return -2; //添加失败
            }
            return 1;

        } else { //其他用户未公开，没有权限
            return -1;
        }
    }

    //复制pRoot的children信息(所有的子书签(夹))到newRoot下
    private static void doCopyBT(Integer pRootID, BookmarkTree newRoot, Integer uid){
        newRoot.setChildren(""); //需要重新加入child
        for(BookmarkTree child : btDao.getAllChild(pRootID)) {
            //System.out.println(child);
            //针对每个child, 需要新建对应的对象，并设置属性: ID、parentID、uid、children
            btIDMax++;
            newRoot.setChildren(newRoot.getChildren() + btIDMax + ",");
            BookmarkTree newChild = new BookmarkTree(child);
            newChild.setID(btIDMax);
            newChild.setParentID(newRoot.getID());
            newChild.setUid(uid);
            copyList.add(newChild);

            if(child.getType() == 0) {
                doCopyBT(child.getID(), newChild, uid);
            }
        }
    }


    //======================插件端的接口==============================

    private static int maxBtID;    //book表中最大的ID序号
    private static int uid;        //用户id, 上传过程中始终不变
    public static List<BookmarkTree> childList = new LinkedList<>(); //保存所有书签(夹)对象
    /**
     * Description: 插件端上传书签树(同步)
     * @param jsonObject: json格式的书签树
     * @return:  上传成功 ? 1 : -1
     */
    public long uploadBT(JSONObject jsonObject) {
        long startTime =  System.currentTimeMillis();

        //System.out.println(jsonObject);
        String uname = jsonObject.getString("userName");
        String password = jsonObject.getString("password");
        User user = userDao.verifyUser(uname, password); //验证用户名和密码是否匹配
        if(user == null) return -1;

        //验证成功，开始上传
        //先处理根结点(默认书签夹，用户注册时默认添加，需要更改children信息)
        Integer rootID = userDao.getRootIDByName(uname);       //当前用户书签树的根结点(默认书签夹)
        maxBtID = userDao.getMaxBookID();         //book表中最大的ID序号
        uid = userDao.getUIDByName(uname);
        BookmarkTree root = btDao.getBookmarkTreeByID(rootID); //需要更新children信息
        if(root == null) return -1;
        root.setChildren("");

        String children = jsonObject.getString("children");//最外层children
        JSONArray rootArr = JSONObject.parseArray(children);    //rootArr始终只有一个对象, 即默认书签夹
        JSONObject rootObj = rootArr.getJSONObject(0);
        children = rootObj.getString("children");          //用户需要上传的书签(夹)信息(加入数据库)
        JSONArray childArr = JSONObject.parseArray(children);

        //处理上传的信息
        int res =  parseJson(root, childArr, rootID);
        if(res == -1) return -1;

        //解析成功
        //先更新根结点的children信息
        //System.out.println(root.getChildren());
        res = btDao.updateBookmarkTreeByID(rootID, root.getChildren());
        if(res == 0) return -1;

        //再将所有解析出的对象加入数据库
        for(BookmarkTree bt : childList) {
            res = userDao.addBook(bt);
            if(res == 0) return -1;
        }

        return (System.currentTimeMillis() - startTime);
    }

    private static Integer parseJson(BookmarkTree parent, JSONArray childArr, Integer rootID) {
        for(int i = 0; i < childArr.size(); i++) {
            maxBtID += 1;
            parent.setChildren(parent.getChildren() + maxBtID + ","); //更新父节点的children信息

            //先默认创建子对象, 之后还需要更新type, title, url, children信息
            BookmarkTree child = new BookmarkTree(maxBtID, rootID, 0, uid, "", "", "", 0, 0);
            childList.add(child);

            //根据json对象，更新child信息
            JSONObject childObj = childArr.getJSONObject(i);
            String title = childObj.getString("title");
            int type = Integer.parseInt(childObj.getString("type"));
            child.setTitle(title); child.setType(type);

            if(type == 0) { //是书签夹
                String children = childObj.getString("children");
                JSONArray newChildArr = JSONObject.parseArray(children);
                parseJson(child, newChildArr, child.getID());
            } else if(type == 1) { //是书签
                String url = childObj.getString("url");
                child.setUrl(url);
            } else {
                return -1;
            }
        }

        return 1;
    }


    private static StringBuilder data = new StringBuilder("\"data\":{\n");
    /**
      * Description: 插件端查找书签树(恢复)
      * @param uid: 用户id
      * @return: json格式的字符串
      */
    public String obtainBT(Integer uid) {
        User user = userDao.getByUId(uid);
        //System.out.println(user);
        String uname = user.getName();
        String password = user.getPassword();
        data.append("\"userName\":").append("\"").append(uname).append("\"").append(",\n");
        data.append("\"password\":").append("\"").append(password).append("\"").append(",\n");

        Integer rootID = user.getRootID();   //用户的书签树根结点ID
        BookmarkTree root = btDao.getBookmarkTreeByID(rootID);

        if(root != null && root.getChildren() != null && root.getIsPublic() == 1) {  //是公开的书签夹
            data.append("\"children\":[\n");
            serializeBT(root);
        } else {
            return "error";
        }

        return data.append("]\n}\n").toString();
    }

    private static void serializeBT(BookmarkTree root) {
        //System.out.println(root);
        String title = root.getTitle();
        int type = root.getType();
        data.append("{\n");
        data.append("\"title\":").append("\"").append(title).append("\",\n");
        data.append("\"type\":").append("\"").append(type).append("\",\n");

        if(type == 0) { //书签夹
            data.append("\"children\":[\n");
            String[] children = root.getChildren().split(",");
            //System.out.println(children.length);
            for(int i = 0; i < children.length; i++) {
                int btID = Integer.parseInt(children[i]);
                //System.out.println(btID);
                BookmarkTree child = btDao.getBookmarkTreeByID(btID);
                serializeBT(child);
                if(i < children.length - 1)
                    data.append(",\n");
            }
            data.append("]\n}\n");
        } else {  //书签
            data.append("\"url\":").append("\"").append(root.getUrl()).append("\"\n");
            data.append("}\n");
        }
    }
}
