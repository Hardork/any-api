package com.hwq.project.exception;

import com.hwq.project.common.ErrorCode;

/**
 * @Author:HWQ
 * @DateTime:2023/4/22 22:55
 * @Description: 自定义异常类,所有的异常都到这处理
 **/
public class BusinessException extends RuntimeException{

    private final int code;

    private final String description;


    public BusinessException(String message, int code, String description){
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode,String description){
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
