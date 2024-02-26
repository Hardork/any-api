package com.hwq.goatapicommon.service;

/**
 * 内部用户接口信息服务
 */
public interface InnerUserInterfaceInfoService {

    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);

    /**
     * 判断用户是否还有调用该接口的次数
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean hasInvokeNum(long interfaceInfoId, long userId);
}
