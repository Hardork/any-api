package com.hwq.project.service.impl.inner;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hwq.goatapicommon.model.dto.InterfaceAccessStatsDTO;
import com.hwq.goatapicommon.service.InnerInterfaceAccessStatsService;
import com.hwq.project.mapper.InterfaceAccessLogsMapper;
import com.hwq.project.mapper.InterfaceAccessStatsMapper;
import com.hwq.project.mapper.InterfaceLocaleStatsMapper;
import com.hwq.project.model.dto.analysis.InterfaceLocaleStatsDTO;
import com.hwq.project.model.entity.InterfaceAccessLogs;
import com.hwq.project.model.entity.InterfaceLocaleStats;
import com.hwq.project.service.InterfaceLocaleStatsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.hwq.project.constant.CommonConstant.AMAP_REMOTE_URL;

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
    private InterfaceLocaleStatsMapper interfaceLocaleStatsMapper;

    @Resource
    private InterfaceAccessLogsMapper interfaceAccessLogsMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private final String statsLocaleAmapKey = "824c511f0997586ea016f979fdb23087";


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
            int weekDay = (DateUtil.dayOfWeekEnum(date).getIso8601Value());
            // 判断uv、uip是否要加1
            AtomicBoolean uvFirstFlag = new AtomicBoolean();
            AtomicBoolean uipFirstFlag = new AtomicBoolean();
            // 缓存中判断
            Long added = redisTemplate.opsForSet().add("interface-uv:" + interfaceId, userId);
            uvFirstFlag.set(added != null && added > 0L);
            Long uipAdded = redisTemplate.opsForSet().add("interface-uip:" + interfaceId, ipAddr);
            uipFirstFlag.set(uipAdded != null && uipAdded > 0L);
            InterfaceAccessStatsDTO interfaceAccessStatsDTO = InterfaceAccessStatsDTO.builder()
                    .interfaceInfoId(interfaceId)
                    .pv(1)
                    .uv(uvFirstFlag.get() ? 1 : 0)
                    .uip(uipFirstFlag.get() ? 1 : 0)
                    .hour(hour)
                    .weekday(weekDay)
                    .date(date)
                    .build();
            // 1.分小时记录接口的pv、uv、uip
            interfaceAccessStatsMapper.interfaceStats(interfaceAccessStatsDTO);
            // 将用户数据插入缓存集合中，用于后续判断uv以及uip是否要+1
            // 2. 记录接口IP对应的区域
            Map<String, Object> localeParamMap = new HashMap<>();
            localeParamMap.put("key", statsLocaleAmapKey);
            localeParamMap.put("ip", ipAddr);
            String localeResultStr = HttpUtil.get(AMAP_REMOTE_URL, localeParamMap);
            JsonElement jsonElement = JsonParser.parseString(localeResultStr);
            // 将 JsonElement 转换为 JsonObject
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String infoCode = jsonObject.get("infocode").getAsString();
            String actualProvince = "未知";
            String actualCity = "未知";
            if (StrUtil.isNotBlank(infoCode) && StrUtil.equals(infoCode, "10000")) {
                String province = jsonObject.get("province").toString();
                boolean unknownFlag = StrUtil.equals(province, "[]");
                InterfaceLocaleStatsDTO interfaceLocaleStats = InterfaceLocaleStatsDTO.builder()
                        .province(actualProvince = unknownFlag ? actualProvince : province)
                        .city(actualCity = unknownFlag ? actualCity : jsonObject.get("city").getAsString())
                        .adcode(unknownFlag ? "未知" : jsonObject.get("adcode").getAsString())
                        .cnt(1)
                        .interfaceInfoId(interfaceId)
                        .country("中国")
                        .date(new Date())
                        .build();
                interfaceLocaleStatsMapper.interfaceLocaleStats(interfaceLocaleStats);
            }
            // 3. 记录接口日志（记录IP、用户、地区）
            InterfaceAccessLogs linkAccessLogsDO = InterfaceAccessLogs.builder()
                    .userId(userId)
                    .ip(ipAddr)
                    .locale(StrUtil.join("-", "中国", actualProvince, actualCity))
                    .interfaceInfoId(interfaceId)
                    .build();
            interfaceAccessLogsMapper.insert(linkAccessLogsDO);
        } catch (Throwable exception) {
            log.error("接口监控统计异常", exception);
        }
    }
}
