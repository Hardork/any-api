package com.hwq.project.mapper;

import com.hwq.project.model.dto.analysis.GetSingleInterfaceStatsRequest;
import com.hwq.project.model.dto.analysis.InterfaceLocaleStatsDTO;
import com.hwq.project.model.entity.InterfaceLocaleStats;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author wqh
* @description 针对表【interface_locale_stats】的数据库操作Mapper
* @createDate 2024-07-02 00:52:21
* @Entity com.hwq.project.model.entity.InterfaceLocaleStats
*/
public interface InterfaceLocaleStatsMapper extends BaseMapper<InterfaceLocaleStats> {

    @Insert("insert into interface_locale_stats(interfaceInfoId, date, cnt, province, city, adcode, country) " +
            "values (#{param.interfaceInfoId}, #{param.date}, #{param.cnt}, #{param.province}, #{param.city}, #{param.adcode}, #{param.country}) on duplicate key update cnt = cnt + #{param.cnt}")
    void interfaceLocaleStats(@Param("param") InterfaceLocaleStatsDTO interfaceLocaleStats);

    /**
     * 根据短链接获取指定日期内地区监控数据
     */
    @Select("SELECT " +
            "    province, " +
            "    SUM(cnt) AS cnt " +
            "FROM " +
            "    interface_locale_stats " +
            "WHERE " +
            "    interfaceInfoId = #{param.interfaceId} " +
            "    AND date BETWEEN #{param.startTime} and #{param.endTime} " +
            "GROUP BY " +
            "    interfaceInfoId, province;")
    List<InterfaceLocaleStats> listLocaleByInterfaceId(@Param("param") GetSingleInterfaceStatsRequest requestParam);
}




