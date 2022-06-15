package com.bookmark.controller;

import com.alibaba.fastjson.JSONObject;
import com.bookmark.pojo.BookmarkTree;
import com.bookmark.service.BookmarkTreeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BookmarkTreeController {

    @Resource
    private BookmarkTreeService btService;

    //https://blog.csdn.net/u010412719/article/details/69788227

    /**
      * Description: 重命名书签(夹)
      * @param bt: 要操作的书签(夹)
	  * @param newName: 新名字
      * @return:  Map<String, String> response:
      *           k-v:  "msg"       :  操作成功 / 权限不够 / 操作失败
      *                 "errorCode" :  1 / -1 / -2
      *                 "httpCode"  :  200
      */
    @GetMapping("/renameBT/{newName}")
    public @ResponseBody Object renameBT(@RequestBody BookmarkTree bt, @PathVariable("newName") String newName) {
        Map<String, String> response = new HashMap<>();
        Integer uid = bt.getUid();
        Integer btID = bt.getID();
        int res = btService.renameBT(uid, btID, newName);
        if(res == 1) {
            response.put("msg", "重命名成功");
            response.put("errorCode", "1");
            response.put("httpCode", "200");
        } else if(res == -1){
            response.put("msg", "权限不够");
            response.put("errorCode", "-1");
            response.put("httpCode", "200");
        } else {
            response.put("msg", "操作错误");
            response.put("errorCode", "-2");
            response.put("httpCode", "200");
        }

        return response;
    }

    /**
      * Description: 公开或私密 某个书签夹(其子书签夹也一致)
      * @param bt: 要操作的书签树
	  * @param isPublic: isPublic == 0 ? 私密 : 公开(1)
      * @return:  Map<String, String> response:
      *           k-v:  "msg"       :  操作成功 / 权限不够 / 操作失败
      *                 "errorCode" :  1 / -1 / -2
      *                 "httpCode"  :  200
      */
    @GetMapping("/publicBTOrNot/{isPublic}")
    public @ResponseBody Object publicBTOrNot(@RequestBody BookmarkTree bt, @PathVariable("isPublic") Integer isPublic) {
        Map<String, String> response = new HashMap<>();
        int res = btService.publicBTOrNot(bt, isPublic);
        if(res == 1) {
            response.put("msg", "操作成功");
            response.put("errorCode", "1");
            response.put("httpCode", "200");
        } else if(res == -1){
            response.put("msg", "权限不够");
            response.put("errorCode", "-1");
            response.put("httpCode", "200");
        } else {
            response.put("msg", "操作失败");
            response.put("errorCode", "-2");
            response.put("httpCode", "200");
        }

        return response;
    }

    /**
     * Description:  删除某个书签夹(其子书签(夹)也一致)
     * @param bt: 要操作的书签树
     * @return:  Map<String, String> response:
     *           k-v:  "msg"       :  操作成功 / 权限不够 / 操作失败
     *                 "errorCode" :  1 / -1 / -2
     *                 "httpCode"  :  200
     */
    @GetMapping("/deleteBT")
    public @ResponseBody Object deleteBT(@RequestBody BookmarkTree bt) {
        Map<String, String> response = new HashMap<>();
        Integer uid = bt.getUid();
        Integer btID = bt.getID();
        int res = btService.deleteBT(uid, btID);
        if(res == 1) {
            response.put("msg", "操作成功");
            response.put("errorCode", "1");
            response.put("httpCode", "200");
        } else if(res == -1){
            response.put("msg", "权限不够");
            response.put("errorCode", "-1");
            response.put("httpCode", "200");
        } else {
            response.put("msg", "操作失败");
            response.put("errorCode", "-2");
            response.put("httpCode", "200");
        }

        return response;
    }

    /**
     * Description:  在某个书签夹下创建书签夹
     * @param parentBT: 要操作的书签树
     * @param btName: 名称
     * @return:  Map<String, String> response:
     *           k-v:  "msg"       :  操作成功 / 操作失败
     *                 "errorCode" :  1 / -1
     *                 "httpCode"  :  200
     */
    @GetMapping("/createBT/{btName}")
    public @ResponseBody Object createBT(@RequestBody BookmarkTree parentBT, @PathVariable("btName") String btName) {
        Map<String, String> response = new HashMap<>();
        Integer uid = parentBT.getUid();
        Integer parentID = parentBT.getID();
        int res = btService.createBT(uid, parentID, btName);
        if(res == 1) {
            response.put("msg", "创建成功");
            response.put("errorCode", "1");
            response.put("httpCode", "200");
        } else {
            response.put("msg", "创建失败");
            response.put("errorCode", "-1");
            response.put("httpCode", "200");
        }
        return response;
    }

    /**
     * Description:  复制某个书签夹(其子书签(夹)也一致)到当前用户(uid)根书签夹下
     * @param bt: 要操作的书签树
     * @return:  Map<String, String> response:
     *           k-v:  "msg"       :  操作成功 / 权限不够 / 操作失败
     *                 "errorCode" :  1 / -1 / -2
     *                 "httpCode"  :  200
     */
    @GetMapping("/copyBT/{uid}")
    public @ResponseBody Object copyBT(@RequestBody BookmarkTree bt, @PathVariable("uid") Integer uid) {
        Map<String, String> response = new HashMap<>();
        Integer pid = bt.getUid();
        Integer btID = bt.getID();
        int res = btService.copyBT(pid, btID, uid);
        if(res == 1) {
            response.put("msg", "操作成功");
            response.put("errorCode", "1");
            response.put("httpCode", "200");
        } else if(res == -1){
            response.put("msg", "权限不够");
            response.put("errorCode", "-1");
            response.put("httpCode", "200");
        } else {
            response.put("msg", "操作失败");
            response.put("errorCode", "-2");
            response.put("httpCode", "200");
        }

        return response;
    }

    /**
     * Description:  上传书签树
     * @param data:  json 格式书签树
     * @return:  Map<String, String> response:
     *           k-v:  "msg"       :  操作成功 / 操作失败
     *                 "errorCode" :  1 / -1
     *                 "httpCode"  :  200
     *                 "data"      :  上传时间(操作成功才有该属性)
     */
    @GetMapping("uploadBT")
    public @ResponseBody Object uploadBT(@RequestBody String data) {
        Map<String, String> response = new HashMap<>();
        JSONObject jsonObject = JSONObject.parseObject(data);

        long res = btService.uploadBT(jsonObject.getJSONObject("data"));

        if(res > 0) {
            response.put("msg", "上传成功");
            response.put("errorCode", "1");
            response.put("httpCode", "200");
            response.put("data",  res + "");
        } else if(res == -1){
            response.put("msg", "上传失败");
            response.put("errorCode", "-1");
            response.put("httpCode", "200");
        }

        return response;
    }

    /**
     * Description: 获取当前用户所有的书签夹信息
     * @param uid:  当前用户
     * @return:  Map<String, String> response:
     *           k-v:  "msg"       :  操作成功 / 操作失败
     *                 "errorCode" :  1 / -1
     *                 "httpCode"  :  200
     *                 "data"      :  json格式的书签树字符串(操作成功才有该属性)
     */
    @GetMapping("obtainBT/{uid}")
    public @ResponseBody Object obtainBT(@PathVariable("uid") Integer uid) {
        Map<String, String> response = new HashMap<>();

        String res = btService.obtainBT(uid);
        if(res.equals("error")) {
            response.put("msg", "获取失败");
            response.put("errorCode", "-1");
            response.put("httpCode", "200");
        } else {
            response.put("msg", "上传成功");
            response.put("errorCode", "1");
            response.put("httpCode", "200");
            response.put("data", res);
        }

        return response;
    }

}
