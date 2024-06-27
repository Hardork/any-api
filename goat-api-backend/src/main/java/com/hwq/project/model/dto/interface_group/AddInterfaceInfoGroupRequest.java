package com.hwq.project.model.dto.interface_group;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

/**
 * @author HWQ
 * @date 2024/6/26 18:13
 * @description
 */
@Data
public class AddInterfaceInfoGroupRequest {
    /**
     * 接口分类名称
     */
    private String name;
}
