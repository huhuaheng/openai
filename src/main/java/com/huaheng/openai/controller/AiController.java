package com.huaheng.openai.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
        log.info("请求openai服务：{}", openaiToken);
        HttpRequest post = HttpUtil.createPost(openAiUrl + chatUrl);
        //1.组装参数
        ChatMessageData chatMessageData = new ChatMessageData();
        chatMessageData.setModel(ConfigConstants.MODEL_3_5);
        chatMessageData.setMessage(CollUtil.newArrayList(new Message("user", question.getQuestion())));
        post.header("Authorization", "Bearer " + openaiToken);
        post.body(JSON.toJSONString(chatMessageData));
        post.timeout(60000000);
        //3.返回结果
        long s = System.currentTimeMillis();
        HttpResponse execute = null;
        try {
            execute = post.execute();
            if (execute.isOk()) {
                return Result.success(JSON.parseObject(execute.body()));
            }
            log.debug("请求openai服务失败：{}", execute.body());
        } catch (Exception e) {
            log.info("请求openai服务耗时：{}", System.currentTimeMillis() - s);
            e.printStackTrace();
        }

        return Result.error("请求openai服务失败");
    }

}
