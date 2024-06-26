package com.hwq.project.config;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class CacheInvalidationListener {

    @Resource
    private Cache<String, Object> localCache;

    public void onMessage(String message) {
        // 清理本地缓存
        log.info("Received message for invalidation: {}", message);
        // 去除多余的双引号
        if (message.startsWith("\"") && message.endsWith("\"")) {
            message = message.substring(1, message.length() - 1);
        }
        localCache.invalidate(message);
    }
}
