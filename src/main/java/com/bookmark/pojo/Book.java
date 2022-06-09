package com.bookmark.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
public class Book {
    private Integer ID;
    private Integer parentID;
    private Integer type;
    private Integer uid;
    private String title;
    private String url;
    private String children;
    private Integer isPublic;
    private Integer favorites;
}
