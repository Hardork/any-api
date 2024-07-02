package com.hwq.project.service;

import com.hwq.goatapicommon.model.entity.InterfaceAccessStats;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hwq.project.model.dto.analysis.GetSingleInterfaceStatsRequest;
import com.hwq.project.model.vo.analysis.InterfaceStatsRespVO;

/**
* @author wqh
* @description 针对表【interface_access_stats】的数据库操作Service
* @createDate 2024-06-30 13:56:10
*/
public interface InterfaceAccessStatsService extends IService<InterfaceAccessStats> {

    /**
     * 查询单个接口的监控信息
     * @param getSingleInterfaceStatsRequest
     * @return
     */
    InterfaceStatsRespVO getSingleInterfaceStats(GetSingleInterfaceStatsRequest getSingleInterfaceStatsRequest);
}
