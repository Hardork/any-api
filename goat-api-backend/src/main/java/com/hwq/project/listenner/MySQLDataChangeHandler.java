package com.hwq.project.listenner;

import com.hwq.goatapicommon.model.entity.InterfaceInfo;
import com.hwq.project.model.dto.interfaceinfo.InterfaceInfoEsDTO;
import com.hwq.project.service.InterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

import javax.annotation.Resource;


@CanalTable("interface_info")
@Component
@Slf4j
public class MySQLDataChangeHandler implements EntryHandler<InterfaceInfo> {

    @Resource
    private InterfaceInfoService interfaceInfoService;
    
    /**
     * mysql中数据有新增时自动执行
     */
    @Override
    public void insert(InterfaceInfo interfaceInfo) {
        try {
            InterfaceInfoEsDTO interfaceInfoEsDTO = new InterfaceInfoEsDTO();
            BeanUtils.copyProperties(interfaceInfo, interfaceInfoEsDTO);
            interfaceInfoService.saveToEs(interfaceInfoEsDTO);
            System.out.println(interfaceInfo);
        } catch (Exception e) {
            log.error("新增ES数据失败" + interfaceInfo);
        }
    }

    /**
     * mysql中数据有修改时自动执行
     * @param before 修改前的数据
     * @param after 修改后的数据
     */
    @Override
    public void update(InterfaceInfo before, InterfaceInfo after) {
        try {
            InterfaceInfoEsDTO interfaceInfoEsDTO = new InterfaceInfoEsDTO();
            BeanUtils.copyProperties(after, interfaceInfoEsDTO);
            interfaceInfoService.saveToEs(interfaceInfoEsDTO);
        } catch (Exception e) {
            log.error("更新ES数据失败" + after);
        }
    }

    /**
     * mysql中数据有删除时自动执行
     * @param interfaceInfo 要删除的数据
     */
    @Override
    public void delete(InterfaceInfo interfaceInfo) {
        try {
            Long id = interfaceInfo.getId();
            if (id == null) {
                log.error("删除接口id为null");
                return;
            }
            interfaceInfoService.deleteFromEsById(id);
        } catch (Exception e) {
            log.error("更新ES数据失败" + interfaceInfo);
        }
    }
}
