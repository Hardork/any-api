package com.hwq.project.mapper;

import com.hwq.goatapicommon.model.dto.InterfaceAccessStatsDTO;
import com.hwq.goatapicommon.model.entity.InterfaceAccessStats;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hwq.project.model.dto.analysis.GetSingleInterfaceStatsRequest;
import com.hwq.project.model.vo.analysis.InterfaceStatsAccessDailyVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    /**
     * 根据接口ID获取指定日期内的基础监控数据
     * @param getSingleInterfaceStatsRequest
     * @return
     */
    @Select("select" +
            "    date, SUM(uv) AS uv, " +
            "    SUM(pv) AS pv, " +
            "    SUM(uip) AS uip " +
            "FROM " +
            "interface_access_stats " +
            "WHERE " +
            "    interfaceInfoId = #{param.interfaceId}" +
            "    and date between #{param.startTime} and #{param.endTime}" +
            "group by " +
            "    interfaceInfoId, date;")
    List<InterfaceStatsAccessDailyVO> listStatsByInterfaceId(@Param("param") GetSingleInterfaceStatsRequest getSingleInterfaceStatsRequest);

    /**
     * 根据接口ID查询指定日期内24小时的PV统计
     * @param getSingleInterfaceStatsRequest
     * @return
     */
    @Select("select " +
            "    hour, " +
            "    SUM(pv) as pv " +
            "FROM " +
            "    interface_access_stats " +
            "where " +
            "    interfaceInfoId = #{param.interfaceId} " +
            "    AND date BETWEEN #{param.startTime} and #{param.endTime}" +
            "GROUP BY " +
            "    interfaceInfoId, hour;")
    List<InterfaceAccessStats> listHourStatsByInterfaceId(@Param("param") GetSingleInterfaceStatsRequest getSingleInterfaceStatsRequest);

    /**
     * 根据接口ID查询1周内的PV统计
     * @param getSingleInterfaceStatsRequest
     * @return
     */
    @Select("SELECT " +
            "    weekday, " +
            "    SUM(pv) AS pv " +
            "FROM " +
            "   interface_access_stats " +
            "WHERE " +
            "    interfaceInfoId = #{param.interfaceId} " +
            "    AND date BETWEEN #{param.startTime} and #{param.endTime} " +
            "GROUP BY " +
            "    interfaceInfoId, weekday;")
    List<InterfaceAccessStats> listWeekdayStatsByInterfaceId(@Param("param") GetSingleInterfaceStatsRequest getSingleInterfaceStatsRequest);
}




