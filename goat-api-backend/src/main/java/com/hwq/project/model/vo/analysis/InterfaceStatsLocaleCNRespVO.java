package com.hwq.project.model.vo.analysis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author HWQ
 * @date 2024/7/1 15:36
 * @description 访问用户所在地区统计返回类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterfaceStatsLocaleCNRespVO {
    /**
     * 统计
     */
    private Integer cnt;

    /**
     * 地区
     */
    private String locale;

    /**
     * 占比
     */
    private Double ratio;
}
