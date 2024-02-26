package com.hwq.goatapiinterface.utils;

import cn.hutool.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * @author HWQ
 * @date 2024/2/24 17:51
 * @description 构建请求类
 */
@Slf4j
public class RequestUtils {

    /**\
     * 构建get请求的url
     * @param baseUrl 基本url
     * @param params 请求参数
     * @return 构建好的url
     */
    public static <T> String buildUrl(String baseUrl, T params)  {
        StringBuilder url = new StringBuilder(baseUrl);
        // 获取参数
        Field[] fields = params.getClass().getFields();
        boolean isFirstParams = true;
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            // 跳过serialVersionUID属性
            if ("serialVersionUID".equals(name)) {
                continue;
            }
            try {
                Object value = field.get(params);
                if (value == null) {
                    continue;
                }
                if (isFirstParams) { // 第一个参数
                    url.append("?").append(name).append("=").append(value);
                    isFirstParams = false;
                } else {
                    url.append("&").append(name).append("=").append(value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return url.toString();
    }

    public static <T> String get(String baseUrl, T params) {
        return get(buildUrl(baseUrl, params));
    }

    public static String get(String url) {
        String body = HttpRequest.get(url).execute().body();
        log.info("[interface] 请求地址:{}, 响应数据:{}", url, body);
        return body;
    }
}
