package com.hwq.project.job;

import com.hwq.goatapicommon.model.entity.InterfaceInfo;
import com.hwq.goatapicommon.model.entity.InterfaceInfoInvokeInfo;
import com.hwq.project.service.InterfaceInfoInvokeInfoService;
import com.hwq.project.service.InterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author HWQ
 * @date 2024/6/26 23:24
 * @description 接口热度计算定时任务
 */
@Component
@Slf4j
public class HotCalculator {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private InterfaceInfoInvokeInfoService interfaceInfoInvokeInfoService;

    /**
     * 时间衰弱指数，G越大热度衰减越快，反之越慢
     */
    private static final double G = 0.8;

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void calculateHot() {
        // todo: 计算热度
        log.info("start calculate hotVal" + LocalDateTime.now());
        List<InterfaceInfoInvokeInfo> list = interfaceInfoInvokeInfoService.list();
        List<InterfaceInfo> hotList = list.stream().map(item -> {
            InterfaceInfo interfaceInfo = new InterfaceInfo();
            // 计算热度
            double hotVal = calHot(item);
            interfaceInfo.setId(item.getId());
            interfaceInfo.setHotVal(hotVal);
            return interfaceInfo;
        }).collect(Collectors.toList());
        boolean update = interfaceInfoService.updateBatchById(hotList);
        if (!update) {
            log.error("update calculate hotVal failed");
        }
    }

    public double calHot(InterfaceInfoInvokeInfo item) {
        Integer initHotValue = item.getInitHotValue();
        Long id = item.getId();
        Integer invokeNum = item.getInvokeNum();
        Date createTime = item.getCreateTime();
        Date today = new Date();
        long diffInMillis = today.getTime() - createTime.getTime();
        long diffInDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
        return (invokeNum + initHotValue) / Math.pow((diffInDays + 1), G);
    }

}
