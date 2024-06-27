package com.hwq.project.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author HWQ
 * @date 2024/6/15 16:39
 * @description
 */
@Configuration
@Slf4j
public class CacheConfig {
    @Bean
    public Cache<String, Object> localCache() {
        return Caffeine.newBuilder()
                .initialCapacity(100) // 设置初始容量
                .maximumSize(500) // 设置最大容量，避免内存溢出
                .expireAfterAccess(5, TimeUnit.MINUTES) // 数据分5钟没访问就失效, 避免缓存太多冷数据
                .removalListener((key, val, cause) -> { // 监听移除的缓存
                    log.info("移除了key" + key + "value:" + val + " cause " + cause);
                })
                .build();
    }
}
