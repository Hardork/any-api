package com.hwq.goatapicommon.service;


import com.hwq.goatapicommon.model.entity.InterfaceInfo;

/**
 * 内部接口信息服务
 *
 * @author <a href="https://github.com/Hardork">HWQ</a>
 */
public interface InnerInterfaceInfoService {

    /**
     * 从数据库中查询模拟接口是否存在（请求路径、请求方法、请求参数）
     */
    InterfaceInfo getInterfaceInfo(String path, String method);
}
