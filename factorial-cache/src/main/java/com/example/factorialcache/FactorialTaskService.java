package com.example.factorialcache;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class FactorialTaskService {

    private StringRedisTemplate redisTemplate;

    public FactorialTaskService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Long saveCalculationTask(int n) {
        redisTemplate.opsForSet().add("factorial:task-queue", String.valueOf(n));
        Long size = redisTemplate.opsForSet().size("factorial:task-queue");
        return size == null ? 0 : size;
    }
}
