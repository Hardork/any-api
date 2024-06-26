package com.hwq.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hwq.goatapicommon.model.entity.InterfaceInfo;
import com.hwq.goatapicommon.model.entity.UserInterfaceInfo;
import com.hwq.project.annotation.AuthCheck;
import com.hwq.project.common.BaseResponse;
import com.hwq.project.common.ErrorCode;
import com.hwq.project.common.ResultUtils;
import com.hwq.project.constant.RedisConstant;
import com.hwq.project.exception.BusinessException;
import com.hwq.project.mapper.InterfaceInfoMapper;
import com.hwq.project.mapper.UserInterfaceInfoMapper;
import com.hwq.project.model.vo.InterfaceInfoVO;
import com.hwq.project.service.InterfaceInfoService;
import com.hwq.project.service.UserInterfaceInfoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author:HWQ
 * @DateTime:2023/7/28 15:56
 * @Description:
 **/
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;
    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private InterfaceInfoService interfaceInfoService;

    @AuthCheck(mustRole = "Admin")
    @GetMapping("/top/interface/invoke")
    @ApiOperation("接口次数统计")
    public BaseResponse<List<InterfaceInfoVO>> listTopInvokeInterface() {
        List<UserInterfaceInfo> userInterfaceInfos = userInterfaceInfoMapper.listTopInvokeInterface(3);
        Map<Long, List<UserInterfaceInfo>> interfaceInfoMap = userInterfaceInfos.stream().collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        QueryWrapper<InterfaceInfo> interfaceInfoQueryWrapper = new QueryWrapper<>();
        interfaceInfoQueryWrapper.in("id", interfaceInfoMap.keySet());
        List<InterfaceInfo> list = interfaceInfoMapper.selectList(interfaceInfoQueryWrapper);
        if (list.isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<InterfaceInfoVO> res = list.stream().map(userInterfaceInfo -> {
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            BeanUtils.copyProperties(userInterfaceInfo, interfaceInfoVO);
            Integer totalNum = interfaceInfoMap.get(userInterfaceInfo.getId()).get(0).getTotalNum();
            interfaceInfoVO.setTotalNum(totalNum);
            return interfaceInfoVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(res);
    }

    @GetMapping("/hot/interface/invoke")
    @ApiOperation("查询热门接口")
    public BaseResponse<List<InterfaceInfo>> listHotInvokeInterface() {
        // 先在redis中查询访问次数最多的10人
        Set<Object> hotInterface = redisTemplate.opsForZSet().range(RedisConstant.HOT_INTERFACE_KEY, 0, 10);
        if (hotInterface == null || hotInterface.isEmpty()) {
            return ResultUtils.success(new ArrayList<>());
        }
        // 根据redis中查询的id，去数据库中返回接口信息
        List<Object> ids = hotInterface.stream().filter(item -> item instanceof Long)
                .map(item -> (Long) item).collect(Collectors.toList());
        String idStr = StringUtils.join(ids, ",");
        List<InterfaceInfo> res = interfaceInfoService.query().in("id", ids).last("order by field(id," + idStr + ")").list();
        return ResultUtils.success(res);
    }



}
