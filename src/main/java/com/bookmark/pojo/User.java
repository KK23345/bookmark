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
    private Integer uId;
    private String uName;
    private String uPassword;
    //TODO 注册相关信息，如手机号或邮箱
}
