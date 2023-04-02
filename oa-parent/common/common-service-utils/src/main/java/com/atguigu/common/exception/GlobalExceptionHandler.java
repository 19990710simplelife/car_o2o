package com.atguigu.common.exception;

/**
 * @ClassName GlobalExceptionHandler
 * @Description
 * @Author simplelife
 * @Date 2023/3/30 17:37
 * @Version 1.0
 */

import com.atguigu.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.fail().message("服务器繁忙，请稍后重试！");
    }
}
