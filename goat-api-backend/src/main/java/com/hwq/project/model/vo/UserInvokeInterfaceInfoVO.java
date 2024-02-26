package com.hwq.project.model.vo;

import com.hwq.goatapicommon.model.entity.InterfaceInfo;
import lombok.Data;

/**
 * @Author:HWQ
 * @DateTime:2023/7/30 16:30
 * @Description:
 **/
@Data
public class UserInvokeInterfaceInfoVO extends InterfaceInfo {
    private Integer leftNum;
    private Integer TotalNum;
}
