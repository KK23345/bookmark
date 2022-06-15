package com.bookmark.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
public class BookmarkTree {

    private Integer ID;         // 书签/书签夹id

    private Integer parentID;   // 书签/书签夹的父书签夹id

    private Integer type;       // 书签夹为0，书签为1

    private Integer uid;        // 所属的user id

    private String title;       // 书签/书签夹名称

    private String url;         //

    private String children;    // 子书签(夹)信息

    private Integer isPublic;   // 是否公开(1:公开; 0:私密)

    private Integer favorites;  // 点赞量

    public BookmarkTree(BookmarkTree bt) {
        this.ID        = bt.getID();
        this.parentID  = bt.getParentID();
        this.type      = bt.getType();
        this.uid       = bt.getUid();
        this.title     = bt.getTitle();
        this.url       = bt.getUrl();
        this.children  = bt.getChildren();
        this.isPublic  = bt.getIsPublic();
        this.favorites = bt.getFavorites();
    }

}
//"tree": {
//   "title": "书签夹1",
//   "type":0,		//书签夹为0，书签为1
//   "children": [     // 书签children
//        {
//          "title":"书签1",
//          "type":1,
//          "url": "https://.....",     // 书签夹url为空
//        },
//        {
//          "title":"书签2",
//          "type":1,
//          "url": "https://.....",     // 书签夹url为空
//        },
//        {
//          "title":"书签夹2",
//          "type":0,
//          "children": [
//              ......
//          ]
//        },
//   ]
//}
