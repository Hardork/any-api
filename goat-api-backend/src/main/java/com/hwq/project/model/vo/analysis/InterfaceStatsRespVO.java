package com.hwq.project.model.vo.analysis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author HWQ
 * @date 2024/7/1 15:35
 * @description 接口监控信息总体返回类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterfaceStatsRespVO {
    /**
     * 访问量
     */
    private Integer pv;

    /**
     * 独立访客数
     */
    private Integer uv;

    /**
     * 独立IP数
     */
    private Integer uip;

    /**
     * 基础访问详情
     */
    private List<InterfaceStatsAccessDailyVO> daily;

    /**
     * 地区访问详情（仅国内）
     */
    private List<InterfaceStatsLocaleCNRespVO> localeCnStats;

    /**
     * 小时访问详情
     */
    private List<Integer> hourStats;

    /**
     * 一周访问详情
     */
    private List<Integer> weekdayStats;

    /**
     * 访客访问类型详情
     */
    private List<InterfaceStatsUvRespVO> uvTypeStats;
}
