package com.hwq.project.mapper;

import com.hwq.goatapicommon.model.dto.InterfaceAccessStatsDTO;
import com.hwq.goatapicommon.model.entity.InterfaceAccessStats;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
* @author wqh
* @description 针对表【interface_access_stats】的数据库操作Mapper
* @createDate 2024-06-30 13:56:10
* @Entity com.hwq.goatapicommon.model.entity.InterfaceAccessStats
*/
public interface InterfaceAccessStatsMapper extends BaseMapper<InterfaceAccessStats> {

    @Insert("insert into interface_access_stats(interfaceInfoId, date, pv, uv, uip, hour, weekday, createTime, updateTime) " +
            "values (#{interfaceAccessStats.interfaceInfoId}, #{interfaceAccessStats.date}, #{interfaceAccessStats.pv},#{interfaceAccessStats.uv},#{interfaceAccessStats.uip},#{interfaceAccessStats.hour},#{interfaceAccessStats.weekday},now(),now()) ON DUPLICATE KEY UPDATE pv = pv + 1, uv = uv + #{interfaceAccessStats.uv}, uip = uip + #{interfaceAccessStats.uip};")
    void interfaceStats(@Param("interfaceAccessStats") InterfaceAccessStatsDTO interfaceAccessStatsDTO);
}




