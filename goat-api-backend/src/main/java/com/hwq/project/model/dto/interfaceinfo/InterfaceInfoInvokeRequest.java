package com.hwq.project.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口调用请求
 *
 * @author <a href="https://github.com/Hardork">HWQ</a>
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {

    /**
     * 主键,接口的id
     */
    private Long id;

    /**
     * 用户请求参数
     */
    private String requestParams;

    private static final long serialVersionUID = 1L;
}
