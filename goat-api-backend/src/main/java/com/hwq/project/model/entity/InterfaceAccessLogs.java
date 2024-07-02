package com.hwq.project.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName interface_access_logs
 */
@TableName(value ="interface_access_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterfaceAccessLogs implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 接口ID
     */
    private Long interfaceInfoId;

    /**
     * 用户信息
     */
    private Long userId;

    /**
     * IP
     */
    private String ip;

    /**
     * 地区
     */
    private String locale;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 删除标识 0：未删除 1：已删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}