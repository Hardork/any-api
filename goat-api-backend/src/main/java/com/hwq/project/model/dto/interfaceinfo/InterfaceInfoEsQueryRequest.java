package com.hwq.project.model.dto.interfaceinfo;

import lombok.Data;

/**
 * @author HWQ
 * @date 2024/7/18 16:14
 * @description
 */
@Data
public class InterfaceInfoEsQueryRequest {
    private Long id;
    private String searchText;
    private String sortField;
    private String sortOrder;
    private Integer gid;
}
