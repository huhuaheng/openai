package com.huaheng.openai.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Message
 * @Description TODO
 * @Author 胡
 * @Date 2023/3/30 16:29
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String role;

    private String content;
}
