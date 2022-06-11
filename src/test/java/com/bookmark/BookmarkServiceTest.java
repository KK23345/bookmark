package com.bookmark;

import com.bookmark.service.BookmarkTreeService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class BookmarkServiceTest {
    @Resource
    private BookmarkTreeService BT;
    @Test
    void renameBtTest(){
        int uid=100;
        int btId=5;
        String name="test00011";
        System.out.println(BT.renameBT(uid,btId,name));
    }
    @Test
    void publicBtTest(){
        int uid=10;
        int btId=1;
        System.out.println(BT.publicBT(uid,btId));
    }

    @Test
    void deleteBtTest(){
        int uid=1;
        int btId=15;
        System.out.println(BT.deleteBT(uid,btId));
    }

    @Test
    void copyBtTest(){
        int uid=1;
        int btId=15;
        System.out.println(BT.deleteBT(uid,btId));
    }

}
