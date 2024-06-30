package com.hwq.project.service.impl.inner;

import cn.hutool.core.date.DateUtil;
import com.hwq.goatapicommon.model.dto.InterfaceAccessStatsDTO;
import com.hwq.goatapicommon.service.InnerInterfaceAccessStatsService;
import com.hwq.project.mapper.InterfaceAccessStatsMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author HWQ
 * @date 2024/6/30 14:14
 * @description
 */
@DubboService
@Slf4j
public class InnerInterfaceAccessStatsServiceImpl implements InnerInterfaceAccessStatsService {

    @Resource
    private InterfaceAccessStatsMapper interfaceAccessStatsMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void interfaceAccessStats(Long interfaceId, Date date, String ipAddr, Long userId) {
        if (interfaceId == null) {
            log.error("接口id为空");
            return;
        }
        if (date == null) {
            log.error("日期为空");
            return;
        }
        try {
            // 获取请求时间
            int hour = DateUtil.hour(date, true);
            int weekDay = (DateUtil.dayOfWeekEnum(date).getValue());
            // 判断uv、uip是否要加1
            AtomicBoolean uvFirstFlag = new AtomicBoolean();
            AtomicBoolean uipFirstFlag = new AtomicBoolean();
            // 缓存中判断
            Long added = redisTemplate.opsForSet().add("interface-uv:" + interfaceId, userId);
            uvFirstFlag.set(added != null && added > 0L);
            Long uipAdded = redisTemplate.opsForSet().add("interface-uip:" + interfaceId, ipAddr);
            uipFirstFlag.set(uipAdded != null && uipAdded > 0L);
            // 构造DTO
            InterfaceAccessStatsDTO interfaceAccessStatsDTO = InterfaceAccessStatsDTO.builder()
                    .interfaceInfoId(interfaceId)
                    .pv(1)
                    .uv(uvFirstFlag.get() ? 1 : 0)
                    .uip(uipFirstFlag.get() ? 1 : 0)
                    .hour(hour)
                    .weekday(weekDay)
                    .date(date)
                    .build();
            // 如果用户在1个小时内已经访问过了，不再统计
            interfaceAccessStatsMapper.interfaceStats(interfaceAccessStatsDTO);
            // 将用户数据插入缓存集合中，用于后续判断uv以及uip是否要+1
        } catch (Throwable exception) {
            log.error("接口监控统计异常", exception);
        }
    }
}
