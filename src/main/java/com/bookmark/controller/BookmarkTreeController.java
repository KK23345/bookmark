package com.bookmark.controller;

import com.bookmark.pojo.BookmarkTree;
import com.bookmark.service.BookmarkTreeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/bt")
public class BookmarkTreeController {

    @Resource
    private BookmarkTreeService btService;


    //======================前端的接口==============================

    @RequestMapping(name = "/renameBT/{newName}", method = RequestMethod.POST)
    public @ResponseBody Object renameBT(@RequestBody BookmarkTree bt, @PathVariable String newName) {
        Map<String, String> response = new HashMap<>();
        Integer uid = bt.getUid();
        Integer btID = bt.getID();
        int res = btService.renameBT(uid, btID, newName);
        if(res == 1) {
            response.put("msg", "重命名成功");
            response.put("errorCode", "1");
            response.put("httpCode", "200");
        }

        return res;
    }


}
