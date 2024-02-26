package com.hwq.goatapiinterface.utils;



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hwq.goatapiclientsdk.exception.ApiException;
import com.hwq.goatapiclientsdk.exception.ErrorCode;
import com.hwq.goatapiclientsdk.model.response.ResultResponse;

import java.util.Map;

import static org.apache.el.util.MessageFactory.get;


/**
 * @Author: HWQ
 * @Date: 2023年09月22日 17:18
 * @Version: 1.0
 * @Description:
 */
public class ResponseUtils {
    public static Map<String, Object> responseToMap(String response) {
        return new Gson().fromJson(response, new TypeToken<Map<String, Object>>() {
        }.getType());
    }

    public static <T> ResultResponse baseResponse(String baseUrl, T params) {
        String response = null;
        try {
            response = get(baseUrl, params);
            Map<String, Object> fromResponse = responseToMap(response);
            boolean success = (boolean) fromResponse.get("success");
            ResultResponse baseResponse = new ResultResponse();
            if (!success) {
                baseResponse.setData(fromResponse);
                return baseResponse;
            }
            fromResponse.remove("success");
            baseResponse.setData(fromResponse);
            return baseResponse;
        } catch (ApiException e) {
            throw new ApiException(ErrorCode.OPERATION_ERROR, "构建url异常");
        }
    }
}