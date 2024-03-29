package com.hwq.project.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 接口调用请求
 *
 * @author <a href="https://github.com/Hardork">HWQ</a>
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private List<InvokeRequest.Field> requestParams;
    private String userRequestParams;

    @Data
    public static class Field {
        private String fieldName;
        private String value;
    }
}
