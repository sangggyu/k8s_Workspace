package com.example.factorialapp;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class FactorialService {

    private StringRedisTemplate redisTemplate;

    public FactorialService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public BigDecimal calculate(int n) {
        if (n<=1) {
            long elapsedTime;
            long startTime = System.currentTimeMillis();
            do {
                elapsedTime = System.currentTimeMillis() - startTime;
            } while (elapsedTime < 500);

            return BigDecimal.ONE;
        }

        return new BigDecimal(n).multiply(calculate(n-1));
    }

}
