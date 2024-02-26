package com.hwq.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hwq.goatapicommon.model.entity.InterfaceInfo;

/**
* @author HWQ
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-05-11 19:57:50
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
