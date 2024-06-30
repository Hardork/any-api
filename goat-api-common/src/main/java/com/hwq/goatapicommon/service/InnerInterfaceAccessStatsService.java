package com.hwq.goatapicommon.service;

import org.springframework.boot.autoconfigure.web.ServerProperties;

import java.util.Date;

/**
 * @author HWQ
 * @date 2024/6/30 17:58
 * @description
 */
public interface InnerInterfaceAccessStatsService {
    /**
     * 接口监控
     * @param InterfaceId 接口id
     * @param date 调用接口的时间
     */
    void interfaceAccessStats(Long InterfaceId, Date date, String ipAddr, Long userId);
}
