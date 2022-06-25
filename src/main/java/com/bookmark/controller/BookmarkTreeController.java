package com.bookmark.controller;

import com.alibaba.fastjson.JSON;
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
     * Description: 获取某个书签夹下的内容（书签夹和书签），向下一层
     * @param data:  url = 47.96.41.120:10030/nextLayer?uid= &btID=
     * @return:  Map<String, String> response:
     *           k-v:  "msg"       :  操作成功 / 操作失败
     *                 "errorCode" :  1 / -1
     *                 "httpCode"  :  200
     *                 "data"      :  json格式的书签树字符串(操作成功才有该属性)
     */
    @GetMapping("/nextLayer")
    public @ResponseBody Object getBtNextLayer(@RequestParam HashMap<String, String> data) {
        Map<String, String> response = new HashMap<>();
        String res = btService.getBtNextLayer(Integer.parseInt(data.get("uid")), Integer.parseInt(data.get("btID")));
        //System.out.println(res);
        JSONObject jsonObject = JSON.parseObject(res);
        //System.out.println(jsonObject);

        if(res.equals("error")) {
            response.put("msg", "获取失败");
            response.put("errorCode", "-1");
            response.put("httpCode", "200");
        } else {
            response.put("msg", "获取成功");
            response.put("errorCode", "1");
            response.put("httpCode", "200");
            //response.put("data", res);
            response.put("data", jsonObject.toJSONString());
        }

        return response;
    }

    /**
      * Description: 重命名书签(夹)
      * @param data: url = 47.96.41.120:10030/renameBT?uid= &btID= &newName=
      * @return:  Map<String, String> response:
      *           k-v:  "msg"       :  操作成功 / 书签(夹)不存在 / 操作失败
      *                 "errorCode" :  1 / -1 / -2
      *                 "httpCode"  :  200
      */
    @GetMapping("/renameBT")
    public @ResponseBody Object renameBT(@RequestParam HashMap<String, String> data) {
        Map<String, String> response = new HashMap<>();
        System.out.println(data.get("uid") + " " + data.get("btID"));
        int res = btService.renameBT(Integer.parseInt(data.get("uid")), Integer.parseInt(data.get("btID")), data.get("newName"));
        if(res == 1) {
            response.put("msg", "操作成功");
            response.put("errorCode", "1");
            response.put("httpCode", "200");
        } else if(res == -1){
            response.put("msg", "书签(夹)不存在");
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
      * @param data: url = 47.96.41.120:10030/publicBTOrNot?uid= &btID= &isPublic= (0:私密；1:公开)
      * @return:  Map<String, String> response:
      *           k-v:  "msg"       :  操作成功 / 权限不够 / 操作失败
      *                 "errorCode" :  1 / -1 / -2
      *                 "httpCode"  :  200
      */
    @GetMapping("/publicBTOrNot")
    public @ResponseBody Object publicBTOrNot(@RequestParam HashMap<String, String> data) {
        Map<String, String> response = new HashMap<>();
        int res = btService.publicBTOrNot(Integer.parseInt(data.get("uid")), Integer.parseInt(data.get("btID")), Integer.parseInt(data.get("isPublic")));
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
     * @param data: url = 47.96.41.120:10030/deleteBT?uid= &btID=
     * @return:  Map<String, String> response:
     *           k-v:  "msg"       :  操作成功 / 权限不够 / 操作失败
     *                 "errorCode" :  1 / -1 / -2
     *                 "httpCode"  :  200
     */
    @GetMapping("/deleteBT")
    public @ResponseBody Object deleteBT(@RequestParam HashMap<String, String> data) {
        Map<String, String> response = new HashMap<>();
        int res = btService.deleteBT(Integer.parseInt(data.get("uid")), Integer.parseInt(data.get("btID")));
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
     * Description:  在某个父书签夹下创建书签夹
     * @param data: url = 47.96.41.120:10030/createBT?uid= &pBtID= (父书签夹ID)&btName=
     * @return:  Map<String, String> response:
     *           k-v:  "msg"       :  操作成功 / 操作失败
     *                 "errorCode" :  1 / -1
     *                 "httpCode"  :  200
     */
    @GetMapping("/createBT")
    public @ResponseBody Object createBT(@RequestParam HashMap<String, String> data) {
        Map<String, String> response = new HashMap<>();
        int res = btService.createBT(Integer.parseInt(data.get("uid")), Integer.parseInt(data.get("pBtID")), data.get("btName"));
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
     * Description:  复制某个书签夹(pid所属用户id, btID, 其子书签(夹)也一致)到当前用户(uid)根书签夹下
     * @param data: url = 47.96.41.120:10030/pid= &btID= &uid=
     * @return:  Map<String, String> response:
     *           k-v:  "msg"       :  操作成功 / 权限不够 / 操作失败
     *                 "errorCode" :  1 / -1 / -2
     *                 "httpCode"  :  200
     */
    @GetMapping("/copyBT")
    public @ResponseBody Object copyBT(@RequestParam HashMap<String, String> data) {
        Map<String, String> response = new HashMap<>();
        int res = btService.copyBT(Integer.parseInt(data.get("pid")), Integer.parseInt(data.get("btID")), Integer.parseInt(data.get("uid")));
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
    @PostMapping("/uploadBT")
    public @ResponseBody Object uploadBT(String data) {
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
    @GetMapping("/obtainBT/{uid}")
    public @ResponseBody Object obtainBT(@PathVariable("uid") Integer uid) {
        Map<String, String> response = new HashMap<>();

        String res = btService.obtainBT(uid);
        //System.out.println(res);
        //res.replaceAll("\\", "");
        JSONObject jsonObject = JSON.parseObject(res);
        //System.out.println(jsonObject);

        if(res.equals("error")) {
            response.put("msg", "获取失败");
            response.put("errorCode", "-1");
            response.put("httpCode", "200");
        } else {
            response.put("msg", "获取成功");
            response.put("errorCode", "1");
            response.put("httpCode", "200");
            //response.put("data", res);
            response.put("data", jsonObject.toJSONString());
        }

        return response;
    }

}
