package com.hwq.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwq.goatapicommon.model.entity.InterfaceInfo;
import com.hwq.goatapicommon.model.entity.User;
import com.hwq.project.common.BaseResponse;
import com.hwq.project.common.ErrorCode;
import com.hwq.project.common.ResultUtils;
import com.hwq.project.constant.CommonConstant;
import com.hwq.project.exception.BusinessException;
import com.hwq.project.model.dto.interface_group.AddInterfaceInfoGroupRequest;
import com.hwq.project.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.hwq.project.model.entity.InterfaceGroup;
import com.hwq.project.service.InterfaceGroupService;
import com.hwq.project.service.InterfaceInfoService;
import com.hwq.project.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author HWQ
 * @date 2024/6/4 20:12
 * @description: 接口分组查询
 */
@RestController
@RequestMapping("/group")
public class InterfaceGroupController {

    @Resource
    private InterfaceGroupService interfaceGroupService;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

    /**
     * 根据分组查询
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<InterfaceInfo>> listGroupInterfaceInfoByPage(InterfaceInfoQueryRequest interfaceInfoQueryRequest, HttpServletRequest request) {
        if (interfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        String sortField = interfaceInfoQueryRequest.getSortField();
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();
        String description = interfaceInfoQuery.getDescription();
        // description 需支持模糊搜索
        interfaceInfoQuery.setDescription(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        queryWrapper.like(StringUtils.isNotBlank(description), "description", description);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        queryWrapper.orderBy(true, false, "hotVal");
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(interfaceInfoPage);
    }

    @PostMapping("/add")
    public BaseResponse<Boolean> addInterfaceInfoGroup(@RequestBody AddInterfaceInfoGroupRequest addInterfaceInfoGroupRequest, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "未登录");
        if (addInterfaceInfoGroupRequest == null) throw new BusinessException(ErrorCode.PARAMS_ERROR);
        String name = addInterfaceInfoGroupRequest.getName();
        if (StringUtils.isEmpty(name)) throw new BusinessException(ErrorCode.PARAMS_ERROR, "分组名称不得为空");
        // 插入分组
        InterfaceGroup interfaceGroup = new InterfaceGroup();
        interfaceGroup.setName(name);
        boolean save = interfaceGroupService.save(interfaceGroup);
        if (!save) throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        return ResultUtils.success(save);
    }


}
