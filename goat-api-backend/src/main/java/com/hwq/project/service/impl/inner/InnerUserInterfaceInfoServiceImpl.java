package com.hwq.project.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hwq.goatapicommon.model.entity.UserInterfaceInfo;
import com.hwq.goatapicommon.service.InnerUserInterfaceInfoService;
import com.hwq.project.service.UserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @Author:HWQ
 * @DateTime:2023/7/21 17:01
 * @Description:
 **/
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invoke(interfaceInfoId, userId);
    }

    @Override
    public boolean hasInvokeNum(long interfaceInfoId, long userId) {
        QueryWrapper<UserInterfaceInfo> userInterfaceInfoQueryWrapper = new QueryWrapper<>();
        userInterfaceInfoQueryWrapper.eq("interfaceInfoId", interfaceInfoId);
        userInterfaceInfoQueryWrapper.eq("userId", userId);
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.getOne(userInterfaceInfoQueryWrapper);
        return userInterfaceInfo.getLeftNum() > 0;
    }

}
