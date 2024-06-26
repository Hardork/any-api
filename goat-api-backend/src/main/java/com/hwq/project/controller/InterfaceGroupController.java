package com.hwq.project.controller;

import com.hwq.project.service.InterfaceGroupService;
import com.hwq.project.service.InterfaceInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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


}
