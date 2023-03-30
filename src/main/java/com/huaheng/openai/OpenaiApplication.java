package com.huaheng.openai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;

@SpringBootApplication
public class OpenaiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenaiApplication.class, args);
    }

}
