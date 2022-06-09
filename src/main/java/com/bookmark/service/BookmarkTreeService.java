package com.bookmark.service;

import com.bookmark.dao.BookmarkTreeDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BookmarkTreeService {

    @Resource
    private BookmarkTreeDao btDao;

        

}
