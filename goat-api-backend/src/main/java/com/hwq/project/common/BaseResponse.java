package com.hwq.project.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author:HWQ
 * @DateTime:2023/4/22 21:33
 * @Description: 通用返回类
 **/
@Data
public class BaseResponse<T> implements Serializable {

    //状态码
    private int code;

    //数据部分
    private T data;

    //返回信息
    private String message;

    //状态信息
    private String description;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public BaseResponse(ErrorCode errorCode){
        this(errorCode.getCode(),null, errorCode.getMessage(),errorCode.getDescription());
    }

    public BaseResponse(int code, String message, String description){
        this(code,null, message,description);
    }

}
