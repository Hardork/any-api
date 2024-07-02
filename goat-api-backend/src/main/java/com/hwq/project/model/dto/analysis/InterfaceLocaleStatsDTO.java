package com.hwq.project.model.dto.analysis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author HWQ
 * @date 2024/7/2 00:56
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterfaceLocaleStatsDTO {
    /**
     * 接口ID
     */
    private Long interfaceInfoId;

    /**
     * 日期
     */
    private Date date;

    /**
     * 访问量
     */
    private Integer cnt;

    /**
     * 省份名称
     */
    private String province;

    /**
     * 市名称
     */
    private String city;

    /**
     * 城市编码
     */
    private String adcode;

    /**
     * 国家标识
     */
    private String country;
}
