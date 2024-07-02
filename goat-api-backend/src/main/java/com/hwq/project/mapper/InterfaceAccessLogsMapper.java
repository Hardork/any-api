package com.hwq.project.mapper;

import com.hwq.goatapicommon.model.dto.InterfaceAccessStatsDTO;
import com.hwq.project.model.dto.analysis.GetSingleInterfaceStatsRequest;
import com.hwq.project.model.entity.InterfaceAccessLogs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* @author wqh
* @description 针对表【interface_access_logs】的数据库操作Mapper
* @createDate 2024-07-01 16:07:01
* @Entity com.hwq.project.model.entity.InterfaceAccessLogs
*/
public interface InterfaceAccessLogsMapper extends BaseMapper<InterfaceAccessLogs> {

    @Select("SELECT " +
            "    COUNT(userId) AS pv, " +
            "    COUNT(DISTINCT userId) AS uv, " +
            "    COUNT(DISTINCT ip) AS uip " +
            "FROM " +
            "     interface_access_logs " +
            "WHERE " +
            "    interfaceInfoId = #{param.interfaceId} " +
            "    AND createTime BETWEEN #{param.startTime} and #{param.endTime} " +
            "GROUP BY " +
            "    interfaceInfoId;")
    InterfaceAccessStatsDTO findPvUvUidStatsByGroup(@Param("param") GetSingleInterfaceStatsRequest getSingleInterfaceStatsRequest);
}




