package com.hwq.project.model.vo.analysis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author HWQ
 * @date 2024/7/1 15:37
 * @description 接口访客类型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterfaceStatsUvRespVO {
    /**
     * 统计
     */
    private Integer cnt;

    /**
     * 访客类型
     */
    private String uvType;

    /**
     * 占比
     */
    private Double ratio;
}
