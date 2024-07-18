package com.hwq.goatapicommon.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口信息
 * @TableName interface_info
 */
@TableName(value ="interface_info")
@Data
public class InterfaceInfo implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 请求头
     */
    private String requestParams;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 接口状态（0-关闭，1-开启）
     */
    private Integer status;

    /**
     * 接口热力值（用于推荐）
     */
    private Double hotVal;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 接口头像
     */
    private String avatarUrl;

    /**
     * 接口响应参数
     */
    private String responseParams;

    /**
     * 请求示例
     */
    private String requestExample;

    /**
     * 减少积分个数
     */
    private Integer reduceScore;

    /**
     * 返回格式
     */
    private String returnFormat;

    /**
     * 创建人
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(0-未删, 1-已删)
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 路径
     */
    private String path;

    /**
     * 所属分组id
     */
    private Integer gid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}