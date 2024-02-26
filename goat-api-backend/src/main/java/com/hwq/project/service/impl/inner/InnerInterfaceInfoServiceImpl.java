package com.hwq.project.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hwq.goatapicommon.model.entity.InterfaceInfo;
import com.hwq.goatapicommon.service.InnerInterfaceInfoService;
import com.hwq.project.common.ErrorCode;
import com.hwq.project.exception.BusinessException;
import com.hwq.project.mapper.InterfaceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @Author:HWQ
 * @DateTime:2023/7/21 17:00
 * @Description:
 **/
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {
    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;
    @Override
    public InterfaceInfo getInterfaceInfo(String path, String method) {
        if (StringUtils.isAnyBlank(path, method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> interfaceInfoQueryWrapper = new QueryWrapper<>();
        interfaceInfoQueryWrapper.eq("url", path);
        interfaceInfoQueryWrapper.eq("method", method);
        return interfaceInfoMapper.selectOne(interfaceInfoQueryWrapper);
    }
}
