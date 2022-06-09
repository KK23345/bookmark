package com.bookmark.controller;

import com.bookmark.service.BookmarkTreeService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class BookmarkTreeController {

    @Resource
    private BookmarkTreeService btService;



}
