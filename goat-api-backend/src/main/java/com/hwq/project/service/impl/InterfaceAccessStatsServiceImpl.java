package com.hwq.project.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwq.goatapicommon.model.dto.InterfaceAccessStatsDTO;
import com.hwq.goatapicommon.model.entity.InterfaceAccessStats;
import com.hwq.project.common.ErrorCode;
import com.hwq.project.exception.BusinessException;
import com.hwq.project.mapper.InterfaceAccessLogsMapper;
import com.hwq.project.mapper.InterfaceLocaleStatsMapper;
import com.hwq.project.model.dto.analysis.GetSingleInterfaceStatsRequest;
import com.hwq.project.model.entity.InterfaceLocaleStats;
import com.hwq.project.model.vo.analysis.InterfaceStatsAccessDailyVO;
import com.hwq.project.model.vo.analysis.InterfaceStatsLocaleCNRespVO;
import com.hwq.project.model.vo.analysis.InterfaceStatsRespVO;
import com.hwq.project.model.vo.analysis.InterfaceStatsUvRespVO;
import com.hwq.project.service.InterfaceAccessStatsService;
import com.hwq.project.mapper.InterfaceAccessStatsMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
* @author wqh
* @description 针对表【interface_access_stats】的数据库操作Service实现
* @createDate 2024-06-30 13:56:10
*/
@Service
public class InterfaceAccessStatsServiceImpl extends ServiceImpl<InterfaceAccessStatsMapper, InterfaceAccessStats>
    implements InterfaceAccessStatsService{

    @Resource
    private InterfaceAccessStatsMapper interfaceAccessStatsMapper;

    @Resource
    private InterfaceLocaleStatsMapper interfaceLocaleStatsMapper;

    @Resource
    private InterfaceAccessLogsMapper interfaceAccessLogsMapper;

    @Override
    public InterfaceStatsRespVO getSingleInterfaceStats(GetSingleInterfaceStatsRequest getSingleInterfaceStatsRequest) {
        Long interfaceId = getSingleInterfaceStatsRequest.getInterfaceId();
        String startTime = getSingleInterfaceStatsRequest.getStartTime();
        String endTime = getSingleInterfaceStatsRequest.getEndTime();
        if (interfaceId == null) throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口ID不得为空");
        if (StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)) throw new BusinessException(ErrorCode.PARAMS_ERROR, "日期不得为空");
        InterfaceStatsRespVO res = new InterfaceStatsRespVO();
        // 统计总的pv、uv、uip
        InterfaceAccessStatsDTO findPvUvUidStatsByGroup = interfaceAccessLogsMapper.findPvUvUidStatsByGroup(getSingleInterfaceStatsRequest);
        res.setUv(findPvUvUidStatsByGroup == null ? 0 : findPvUvUidStatsByGroup.getUv());
        res.setPv(findPvUvUidStatsByGroup == null ? 0 : findPvUvUidStatsByGroup.getPv());
        res.setUip(findPvUvUidStatsByGroup == null ? 0 : findPvUvUidStatsByGroup.getPv());
        // 1.获取指定日期的访问数据
        List<InterfaceStatsAccessDailyVO> interfaceStatsAccessDailyVOS = interfaceAccessStatsMapper.listStatsByInterfaceId(getSingleInterfaceStatsRequest);
        List<InterfaceStatsAccessDailyVO> daily = new ArrayList<>();
        List<String> rangeDates = DateUtil.rangeToList(DateUtil.parse(startTime), DateUtil.parse(endTime), DateField.DAY_OF_MONTH)
                .stream()
                .map(DateUtil::formatDate)
                .collect(Collectors.toList());
        // 查出对应日期的记录
        rangeDates.forEach(each -> {
            Optional<InterfaceStatsAccessDailyVO> optional = interfaceStatsAccessDailyVOS.stream()
                            .filter(item -> Objects.equals(each, item.getDate()))
                            .findFirst();
            if (optional.isPresent()) { // 存在对应日期的记录
                optional.ifPresent(item -> {
                    InterfaceStatsAccessDailyVO addItem = InterfaceStatsAccessDailyVO.builder()
                            .uv(item.getUv())
                            .pv(item.getPv())
                            .uip(item.getUip())
                            .date(each)
                            .build();
                    daily.add(addItem);
                });
            } else {
                InterfaceStatsAccessDailyVO addItem = InterfaceStatsAccessDailyVO.builder()
                        .uv(0)
                        .pv(0)
                        .uip(0)
                        .date(each)
                        .build();
                daily.add(addItem);
            }
            }
        );
        res.setDaily(daily);
        // 2.查询地区信息
        List<InterfaceLocaleStats> interfaceLocaleStats = interfaceLocaleStatsMapper.listLocaleByInterfaceId(getSingleInterfaceStatsRequest);
        List<InterfaceStatsLocaleCNRespVO> areaRes = new ArrayList<>();
        // 访问总和
        int sum = interfaceLocaleStats.stream()
                .mapToInt(InterfaceLocaleStats::getCnt)
                .sum();
        interfaceLocaleStats.forEach(each -> {
            double ratio = (double) each.getCnt() / sum;
            double actualRatio = Math.round(ratio * 100.0) / 100.0;
            InterfaceStatsLocaleCNRespVO localeCNRespVO = InterfaceStatsLocaleCNRespVO.builder()
                    .cnt(each.getCnt())
                    .locale(each.getProvince())
                    .ratio(actualRatio)
                    .build();
            areaRes.add(localeCNRespVO);
        });
        res.setLocaleCnStats(areaRes);
        // 3.查询24h访问记录
        List<InterfaceAccessStats> hourStatsByInterfaceId = interfaceAccessStatsMapper.listHourStatsByInterfaceId(getSingleInterfaceStatsRequest);
        List<Integer> hourStats = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            Integer hour = new Integer(i);
            int hourCnt = hourStatsByInterfaceId.stream()
                    .filter(item -> Objects.equals(item.getHour(), hour))
                    .findFirst()
                    .map(InterfaceAccessStats::getPv)
                    .orElse(0);
            hourStats.add(hourCnt);
        }
        res.setHourStats(hourStats);
        // 4.一周内的PV统计
        List<Integer> weekdayStats = new ArrayList<>();
        List<InterfaceAccessStats> listWeekdayStatsByGroup = interfaceAccessStatsMapper.listWeekdayStatsByInterfaceId(getSingleInterfaceStatsRequest);
        for (int i = 1; i < 8; i++) {
            AtomicInteger weekday = new AtomicInteger(i);
            int weekdayCnt = listWeekdayStatsByGroup.stream()
                    .filter(each -> Objects.equals(each.getWeekday(), weekday.get()))
                    .findFirst()
                    .map(InterfaceAccessStats::getPv)
                    .orElse(0);
            weekdayStats.add(weekdayCnt);
        }
        res.setWeekdayStats(weekdayStats);
        return res;
    }
}




