package com.huaheng.openai.common;

import lombok.Data;

/**
 * @ClassName Result
 * @Description TODO
 * @Author 胡
 * @Date 2023/3/24 14:54
 * @Version 1.0
 **/

@Data
public class Result<T> {

    private Integer code;

    private String msg;

    private T data;

    public Result() {
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Result success() {
        return new Result(200, "success");
    }

    public static Result success(Object data) {
        Result result = new Result(200, "success");
        result.setData(data);
        return result;
    }

    public static Result error() {
        return new Result(500, "error");
    }

    public static Result error(String msg) {
        return new Result(500, msg);
    }

    public static Result error(Integer code, String msg) {
        return new Result(code, msg);
    }









}
