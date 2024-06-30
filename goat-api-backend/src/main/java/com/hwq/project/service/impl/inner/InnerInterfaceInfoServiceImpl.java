package com.hwq.project.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.hwq.goatapicommon.model.entity.InterfaceInfo;
import com.hwq.goatapicommon.service.InnerInterfaceInfoService;
import com.hwq.project.common.ErrorCode;
import com.hwq.project.constant.RedissonLockConstant;
import com.hwq.project.exception.BusinessException;
import com.hwq.project.mapper.InterfaceInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
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

    @Resource
    private RBloomFilter<String> interfaceInfoBloomFilter;

    @Resource
    private RedissonClient redissonClient;

    @Override
    public InterfaceInfo getInterfaceInfo(String path, String method) {
        if (StringUtils.isAnyBlank(path, method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 多级缓存获取接口信息
        String k = INTERFACE_INFO_PREFIX + path + ":" + method;
        return (InterfaceInfo) localCache.get(k, (key) -> {
            // 没获取到缓存，去Redis中拿
            InterfaceInfo interfaceInfo = (InterfaceInfo) redisTemplate.opsForValue().get(key);
            if (interfaceInfo != null) {
                return interfaceInfo;
            } else {
                // 避免缓存穿透
//                if (!interfaceInfoBloomFilter.contains(k)) { // 避免缓存击穿
//                    return null;
//                }
                // 避免缓存击穿
                RLock lock = redissonClient.getLock(String.format(RedissonLockConstant.INTERFACE_LOCK, key));
                lock.lock();
                try {
                    // 双重检索, 如果前面的线程已经把缓存加载了，就没必要再去数据库获取数据
                    interfaceInfo =  (InterfaceInfo) redisTemplate.opsForValue().get(key);
                    if (interfaceInfo != null) {
                        return interfaceInfo;
                    }
                    // redis中也没有，去DB中获取
                    InterfaceInfo interfaceInfoFromDB = getInterfaceInfoFromDB(path, method);
                    // 设置1h的缓存，避免缓存存储太多接口信息
                    redisTemplate.opsForValue().set(key, interfaceInfoFromDB, 1, TimeUnit.HOURS);
                    return interfaceInfoFromDB;
                } finally {
                    lock.unlock();
                }
            }
        });
    }

    public InterfaceInfo getInterfaceInfoFromDB(String path, String method) {
        // 从DB中获取数据
        log.info("get interfaceInfo from db");
        QueryWrapper<InterfaceInfo> interfaceInfoQueryWrapper = new QueryWrapper<>();
        interfaceInfoQueryWrapper.eq("url", path);
        interfaceInfoQueryWrapper.eq("method", method);
        InterfaceInfo info = interfaceInfoMapper.selectOne(interfaceInfoQueryWrapper);
        if (info == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不存在对应方法");
        }
        return info;
    }
}
