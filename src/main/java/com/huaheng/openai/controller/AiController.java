package com.huaheng.openai.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.huaheng.openai.common.ConfigConstants;
import com.huaheng.openai.common.Result;
import com.huaheng.openai.entity.ChatMessageData;
import com.huaheng.openai.entity.Message;
import com.huaheng.openai.entity.dto.ChatDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName AiController
 * @Description TODO
 * @Author 胡
 * @Date 2023/3/30 16:21
 * @Version 1.0
 **/
@Slf4j
@Api(tags = {"AI管理"})
@RequestMapping("/_ai")
@RestController
public class AiController {

    @Value("${openai.url:https://api.openai.com}")
    private String openAiUrl;

    @Value("${openai.token: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}")
    private String openaiToken;

    @Value("${openai.chat:/v1/chat/completions}")
    private String chatUrl;

    @ApiOperation("请求openai服务")
    @RequestMapping(value = "/chat", method = RequestMethod.POST)
    public Result chat(@RequestBody ChatDTO question) {
        HttpRequest post = HttpUtil.createPost(openAiUrl + chatUrl);
        //1.组装参数
        ChatMessageData chatMessageData = new ChatMessageData();
        chatMessageData.setModel(ConfigConstants.MODEL_3_5);
        chatMessageData.setMessage(CollUtil.newArrayList(new Message("user",question.getQuestion())));
        post.header("Authorization", "Bearer " + openaiToken);
        post.body(JSON.toJSONString(chatMessageData));
        post.timeout(60000 * 10);
        //3.返回结果
        HttpResponse execute = post.execute();
        if (execute.isOk()) {
            log.debug("请求成功:{}", execute.body());
            return Result.success(JSON.parseObject(execute.body()));
        }
        log.debug("请求失败:{}", execute.body());
        return Result.error(execute.body());
    }

}
