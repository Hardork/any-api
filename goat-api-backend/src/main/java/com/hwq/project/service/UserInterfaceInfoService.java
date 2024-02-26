package com.hwq.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hwq.goatapicommon.model.entity.User;
import com.hwq.goatapicommon.model.entity.UserInterfaceInfo;
import com.hwq.project.model.dto.userinterfaceinfo.UserInterfaceInfoFreeRequest;
import com.hwq.project.model.vo.UserInvokeInterfaceInfoVO;

import java.util.List;

/**
* @author HWQ
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2023-07-14 17:27:59
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean b);

    /**
     * 用户调用接口
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invoke(long interfaceInfoId, long userId);

    /**
     * 用户获取免费调用的次数
     * @param userInterfaceInfoFreeRequest
     * @param loginUser
     * @return
     */
    long getFreeInvokeNum(UserInterfaceInfoFreeRequest userInterfaceInfoFreeRequest, User loginUser);

    /**
     * 查询用户调用的接口信息
     * @param loginUser
     * @return
     */
    List<UserInvokeInterfaceInfoVO> listUserInvokeInterfaceInfo(User loginUser);

}
