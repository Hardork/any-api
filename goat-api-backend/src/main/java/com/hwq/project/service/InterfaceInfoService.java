package com.hwq.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hwq.goatapicommon.model.entity.InterfaceInfo;
import com.hwq.project.model.dto.interfaceinfo.InterfaceInfoEsDTO;
import com.hwq.project.model.dto.interfaceinfo.InterfaceInfoEsQueryRequest;

import java.util.List;

/**
* @author HWQ
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-05-11 19:57:50
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);

    InterfaceInfoEsDTO saveToEs(InterfaceInfoEsDTO interfaceInfoEsDTO);

    void deleteFromEsById(Long id);

    List<InterfaceInfoEsDTO> searchFromEs(InterfaceInfoEsQueryRequest interfaceInfoEsQueryRequest);
}
