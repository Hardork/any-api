package com.hwq.project.common;

/**
 * @Author:HWQ
 * @DateTime:2023/4/22 22:20
 * @Description: 返回工具类
 **/
public class ResultUtils {

    /**
     * 成功返回
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0, data, "ok","");
    }

    public static BaseResponse error(ErrorCode errorCode){
        return new BaseResponse<>(errorCode);
    }


    public static BaseResponse error(int code, String message, String description) {
        return new BaseResponse<>(code,message,description);
    }
}
