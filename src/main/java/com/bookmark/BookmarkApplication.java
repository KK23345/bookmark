package com.bookmark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class BookmarkApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BookmarkApplication.class, args);
    }

    //发布时需要添加
    @Override
    public SpringApplicationBuilder createSpringApplicationBuilder() {
        return new SpringApplicationBuilder(BookmarkApplication.class);
    }

}
