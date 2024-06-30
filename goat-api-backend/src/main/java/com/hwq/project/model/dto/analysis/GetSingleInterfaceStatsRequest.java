package com.hwq.project.model.dto.analysis;

import lombok.Data;

/**
 * @author HWQ
 * @date 2024/7/1 02:37
 * @description 获取单个接口监控信息请求类
 */
@Data
public class GetSingleInterfaceStatsRequest {
    /**
     * 接口ID
     */
    private Long interfaceId;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
}
