package com.hwq.project.model.dto.interfaceinfo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.hwq.goatapicommon.model.entity.InterfaceInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 创建请求
 *
 * @TableName product
 */
@Data
public class InterfaceInfoAddRequest implements Serializable {

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
    private List<RequestParamsField> requestParams;

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
    private List<ResponseParamsField> responseParams;

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


    private String path;

    private static final long serialVersionUID = 1L;
}
