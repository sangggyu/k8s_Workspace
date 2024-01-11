package com.example.factorialcache;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;


@Service
public class FactorialCacheService {

    private StringRedisTemplate redisTemplate;

    public FactorialCacheService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public BigDecimal cachedFactorial(int n) {
        Object result = redisTemplate.opsForHash().get("factorial:result-set", String.valueOf(n));
        if (result != null) {
            return new BigDecimal(result.toString());
        }
        return null;
    }

    public void cacheFactorial(int n, BigDecimal result) {
        redisTemplate.opsForHash().put("factorial:result-set", String.valueOf(n), result.toPlainString());
    }

}

