package com.hwq.project.model.dto.interface_group;

import lombok.Data;

/**
 * @author HWQ
 * @date 2024/6/4 20:19
 * @description
 */
@Data
public class SelectInterfaceListByGroupRequest {

    /**
     * 接口分类
     */
    private Integer groupId;

    /**
     * 接口名称
     */
    private String interfaceName;
}
