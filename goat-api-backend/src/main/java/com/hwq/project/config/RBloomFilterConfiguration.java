package com.hwq.project.config;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HWQ
 * @date 2024/6/2 19:50
 * @description 布隆过滤器配置
 */
@Configuration
public class RBloomFilterConfiguration {
    /**
     * 用户名布隆过滤器
     * @return
     */
    @Bean
    public RBloomFilter<String> userNameBloomFilter(RedissonClient redissonClient) {
        RBloomFilter<String> userNameBloomFilter = redissonClient.getBloomFilter("userNameBloomFilter");
        userNameBloomFilter.tryInit(100000000L, 0.001);
        return userNameBloomFilter;
    }
}
