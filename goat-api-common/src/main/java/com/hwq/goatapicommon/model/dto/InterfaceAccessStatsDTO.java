package com.hwq.goatapicommon.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author HWQ
 * @date 2024/6/30 14:01
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterfaceAccessStatsDTO {
    /**
     * 接口信息表
     */
    private Long interfaceInfoId;

    /**
     * 日期
     */
    private Date date;

    /**
     * 接口访问量
     */
    private Integer pv;

    /**
     * 接口独立访客数
     */
    private Integer uv;

    /**
     * 接口独立IP数
     */
    private Integer uip;

    /**
     * 小时
     */
    private Integer hour;

    /**
     * 星期
     */
    private Integer weekday;
}
