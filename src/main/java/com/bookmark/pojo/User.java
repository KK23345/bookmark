package com.bookmark.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
public class User {

    private Integer uid;     //

    private String name;

    private String password;

    private Integer rootID;

}
