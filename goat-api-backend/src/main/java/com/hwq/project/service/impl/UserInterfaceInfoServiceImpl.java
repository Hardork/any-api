package com.hwq.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwq.goatapicommon.model.entity.InterfaceInfo;
import com.hwq.goatapicommon.model.entity.User;
import com.hwq.goatapicommon.model.entity.UserInterfaceInfo;
import com.hwq.project.common.ErrorCode;
import com.hwq.project.constant.RedisConstant;
import com.hwq.project.exception.BusinessException;
import com.hwq.project.mapper.UserInterfaceInfoMapper;
import com.hwq.project.model.dto.userinterfaceinfo.UserInterfaceInfoFreeRequest;
import com.hwq.project.model.vo.UserInvokeInterfaceInfoVO;
import com.hwq.project.service.InterfaceInfoService;
import com.hwq.project.service.UserInterfaceInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
* @author HWQ
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
* @createDate 2023-07-14 17:27:59
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{

    @Resource
    private InterfaceInfoService interfaceInfoService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean b) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = userInterfaceInfo.getId();
        Integer leftNum = userInterfaceInfo.getLeftNum();
        Integer totalNum = userInterfaceInfo.getTotalNum();
        // 创建时，所有参数必须非空
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (leftNum < 0 || totalNum < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }

    /**
     * 调用API统计
     */
    public boolean invoke(long interfaceInfoId, long userId) {
        // 校验参数
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo 给操作数据库加锁
        // 增加api的调用次数
        redisTemplate.opsForZSet().incrementScore(RedisConstant.HOT_INTERFACE_KEY, interfaceInfoId + "", 1);
        // 更新用户调用表
        UpdateWrapper<UserInterfaceInfo> userInterfaceInfoUpdateWrapper = new UpdateWrapper<>();
        userInterfaceInfoUpdateWrapper.eq("userId", userId);
        userInterfaceInfoUpdateWrapper.eq("interfaceInfoId", interfaceInfoId);
        userInterfaceInfoUpdateWrapper.gt("leftNum", 0);
        // 更新用户的还可以调用该API剩余次数以及用户已调用改API的次数
        userInterfaceInfoUpdateWrapper.setSql("leftNum = leftNum - 1, totalNum = totalNum + 1");
        return this.update(userInterfaceInfoUpdateWrapper);
    }

    @Override
    public long getFreeInvokeNum(UserInterfaceInfoFreeRequest userInterfaceInfoFreeRequest, User loginUser) {
        if (userInterfaceInfoFreeRequest == null || userInterfaceInfoFreeRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long interfaceInfoId = userInterfaceInfoFreeRequest.getId();
        // 1.查询是否有这接口
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(interfaceInfoId);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int leftNum = 0;
        // 2.查询用户是否调用过这个接口，没有就插入用户调用表
        Long userId = loginUser.getId();
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        queryWrapper.eq("interfaceInfoId", interfaceInfoId);
        UserInterfaceInfo userInterfaceInfo = this.getOne(queryWrapper);
        if (userInterfaceInfo == null) { // 插入新的记录
            UserInterfaceInfo newUserInterfaceInfo = new UserInterfaceInfo();
            newUserInterfaceInfo.setUserId(userId);
            newUserInterfaceInfo.setInterfaceInfoId(interfaceInfoId);
            newUserInterfaceInfo.setTotalNum(0);
            newUserInterfaceInfo.setLeftNum(10);
            leftNum = 10;
            boolean save = this.save(newUserInterfaceInfo);
            if (!save) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        } else { // 更新记录
            leftNum = userInterfaceInfo.getLeftNum() + 10;
            userInterfaceInfo.setLeftNum(leftNum);
            this.updateById(userInterfaceInfo);
        }
        return leftNum;
    }

    @Override
    public List<UserInvokeInterfaceInfoVO> listUserInvokeInterfaceInfo(User loginUser) {
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 根据用户查询记录
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", loginUser.getId());
        List<UserInterfaceInfo> list = this.list(queryWrapper);
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        List<UserInvokeInterfaceInfoVO> res = list.stream().map(userInterfaceInfo -> {
            UserInvokeInterfaceInfoVO userInvokeInterfaceInfoVO = new UserInvokeInterfaceInfoVO();
            // 根据记录中的InterfaceInfoId查询InterfaceInfo，放入结果中
            InterfaceInfo interfaceInfo = interfaceInfoService.getById(userInterfaceInfo.getInterfaceInfoId());
            BeanUtils.copyProperties(interfaceInfo, userInvokeInterfaceInfoVO);
            userInvokeInterfaceInfoVO.setLeftNum(userInterfaceInfo.getLeftNum());
            userInvokeInterfaceInfoVO.setTotalNum(userInterfaceInfo.getTotalNum());
            return userInvokeInterfaceInfoVO;
        }).collect(Collectors.toList());

        return res;
    }
}




