package com.example.factorialcache;

import com.example.factorialcache.util.FileLogger;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FactorialCacheService {

    private final Map<Integer, BigDecimal> factorialMap = new ConcurrentHashMap<>();
    private final FileLogger fileLogger;
    private final ObjectMapper objectMapper;

    public FactorialCacheService(FileLogger fileLogger, ObjectMapper objectMapper) {
        this.fileLogger = fileLogger;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void loadCache() {
        File cacheFile = new File("/factorial/cache/cache.json");
        if (cacheFile.exists()) {
            try {
                Map<Integer, BigDecimal> storedCache = objectMapper
                        .readValue(cacheFile, new TypeReference<>() {});
                factorialMap.putAll(storedCache);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public BigDecimal cachedFactorial(int n) {
        BigDecimal result = factorialMap.getOrDefault(n, null);
        fileLogger.log(result == null ? "Cache missed " + n : "Cache Hit " + n + "!=" + result);
        return result;
    }

    public void cacheFactorial(int n, BigDecimal result) {
        fileLogger.log("Cache factorial " + n + "!=" + result);
        factorialMap.put(n, result);
    }

    @Scheduled(fixedDelay = 1000)
    public void storeCache() {
        try {
            objectMapper.writeValue(new File("/factorial/cache/cache.json"), factorialMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

