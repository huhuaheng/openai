package com.huaheng.openai.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName ChatMessageData
 * @Description TODO
 * @Author 胡
 * @Date 2023/3/30 16:28
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageData {
    private String model;

    private List<Message> messages;
}
