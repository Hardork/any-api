package com.hwq.project.model.vo.analysis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author HWQ
 * @date 2024/7/1 02:40
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterfaceStatsAccessDailyVO {
    /**
     * 日期
     */
    private String date;

    private Integer pv;

    private Integer uv;

    private Integer uip;
}
