package com.atguigu.common.exception;

/**
 * 自定义异常类
 *
 * @ClassName ServiceException
 * @Description
 * @Author simplelife
 * @Date 2023/3/30 17:41
 * @Version 1.0
 */

import com.atguigu.common.ResultCodeEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 自定义全局异常类
 */
@Getter
@Setter
public class ServiceException extends RuntimeException {

    private Integer code;

    private String message;

    /**
     * 通过状态码和错误消息创建异常对象
     *
     * @param code    状态码
     * @param message 异常消息
     */
    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 接收枚举类型对象
     *
     * @param resultCodeEnum 枚举对象
     */
    public ServiceException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }
}
