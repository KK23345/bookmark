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

    @PostMapping("/renameBT/{newName}")
    public @ResponseBody Object renameBT(@RequestBody BookmarkTree bt, @PathVariable String newName) {
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
            response.put("msg", "未知错误");
            response.put("errorCode", "-2");
            response.put("httpCode", "200");
        }

        return response;
    }

    /**
      * Description: 公开或私密 某个书签夹(其子书签夹也一致)
      * @param bt: 要操作的书签树
	  * @param isPublic: isPublic == 0 ? 私密 : 公开(1)
      * @return:
      */
    @PostMapping("/publicBTOrNot/{isPublic}")
    public @ResponseBody Object publicBTOrNot(@RequestBody BookmarkTree bt, @PathVariable Integer isPublic) {
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
            response.put("msg", "未知错误");
            response.put("errorCode", "-2");
            response.put("httpCode", "200");
        }

        return response;
    }

    @PostMapping("/deleteBT")
    public @ResponseBody Object deleteBT(@RequestBody BookmarkTree bt) {
        Map<String, String> response = new HashMap<>();
        Integer uid = bt.getUid();
        Integer btID = bt.getID();
        int res = btService.deleteBT(uid, btID);
        if(res == 1) {
            response.put("msg", "删除书签夹成功");
            response.put("errorCode", "1");
            response.put("httpCode", "200");
        } else if(res == -1){
            response.put("msg", "权限不够");
            response.put("errorCode", "-1");
            response.put("httpCode", "200");
        } else {
            response.put("msg", "未知错误");
            response.put("errorCode", "-2");
            response.put("httpCode", "200");
        }

        return response;
    }

    @PostMapping("/copyBT/{uid}")
    public @ResponseBody Object copyBT(@RequestBody BookmarkTree bt, @PathVariable Integer uid) {
        Map<String, String> response = new HashMap<>();
        Integer pid = bt.getUid();
        Integer btID = bt.getID();
        int res = btService.copyBT(pid, btID, uid);
        if(res == 1) {
            response.put("msg", "复制书签夹成功");
            response.put("errorCode", "1");
            response.put("httpCode", "200");
        } else if(res == -1){
            response.put("msg", "权限不够");
            response.put("errorCode", "-1");
            response.put("httpCode", "200");
        } else {
            response.put("msg", "未知错误");
            response.put("errorCode", "-2");
            response.put("httpCode", "200");
        }

        return response;
    }

    @PostMapping("uploadBT")
    public @ResponseBody Object uploadBT(@RequestBody String data) {
        Map<String, String> response = new HashMap<>();
        JSONObject jsonObject = JSONObject.parseObject(data);

        long res = btService.uploadBT(jsonObject.getJSONObject("data"));

        if(res > 0) {
            response.put("msg", "上传成功");
            response.put("errorCode", "1");
            response.put("httpCode", "200");
            response.put("data", "{time:" + res + "}");
        } else if(res == -1){
            response.put("msg", "上传失败");
            response.put("errorCode", "-1");
            response.put("httpCode", "200");
        }

        return response;
    }

    @PostMapping("obtainBT/{uid}")
    public @ResponseBody Object obtainBT(@PathVariable Integer uid) {
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
