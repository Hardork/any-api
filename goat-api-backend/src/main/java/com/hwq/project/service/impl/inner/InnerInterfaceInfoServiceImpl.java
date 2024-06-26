package com.hwq.project.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.hwq.goatapicommon.model.entity.InterfaceInfo;
import com.hwq.goatapicommon.service.InnerInterfaceInfoService;
import com.hwq.project.common.ErrorCode;
import com.hwq.project.exception.BusinessException;
import com.hwq.project.mapper.InterfaceInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

import java.util.concurrent.TimeUnit;

import static com.hwq.project.constant.LocalCacheConstant.INTERFACE_INFO_PREFIX;

/**
 * @Author:HWQ
 * @DateTime:2023/7/21 17:00
 * @Description:
 **/
@DubboService
@Slf4j
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {
    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Resource
    private Cache<String, Object> localCache;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public InterfaceInfo getInterfaceInfo(String path, String method) {
        if (StringUtils.isAnyBlank(path, method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo: 将缓存修改为多级缓存
        String k = INTERFACE_INFO_PREFIX + path + ":" + method;
        InterfaceInfo res = (InterfaceInfo)localCache.get(k, (key) -> {
            InterfaceInfo interfaceInfo = (InterfaceInfo) redisTemplate.opsForValue().get(key);
            if (interfaceInfo != null) {
                return interfaceInfo;
            }
            // 从DB中获取数据
            log.info("get data from database");
            QueryWrapper<InterfaceInfo> interfaceInfoQueryWrapper = new QueryWrapper<>();
            interfaceInfoQueryWrapper.eq("url", path);
            interfaceInfoQueryWrapper.eq("method", method);
            interfaceInfo = interfaceInfoMapper.selectOne(interfaceInfoQueryWrapper);
            if (interfaceInfo == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "不存在对应方法");
            }
            redisTemplate.opsForValue().set(key, interfaceInfo, 3, TimeUnit.MINUTES);
            return interfaceInfo;
        });
        System.out.println(res);
        return res;
    }
}
