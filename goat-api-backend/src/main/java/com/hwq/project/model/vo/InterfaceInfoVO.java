package com.hwq.project.model.vo;

import com.hwq.goatapicommon.model.entity.InterfaceInfo;
import lombok.Data;

/**
 * @Author:HWQ
 * @DateTime:2023/7/28 15:58
 * @Description: 接口信息封装类
 **/
@Data
public class InterfaceInfoVO extends InterfaceInfo {

    /**
     * 调用次数
     */
    private Integer totalNum;

}
