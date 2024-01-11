package com.example.factorialcache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class FactorialCacheController {
    @Value("${factorial.language}")
    private String language;

    @Value("${factorial.api-key}")
    private String apiKey;

    private FactorialCacheService cacheService;
    private FactorialCalculateService calculateService;
    private FactorialTaskService taskService;

    public FactorialCacheController(FactorialCacheService cacheService, FactorialCalculateService calculateService, FactorialTaskService taskService) {
        this.cacheService = cacheService;
        this.calculateService = calculateService;
        this.taskService = taskService;
    }

    @GetMapping("/factorial/{n}")
    public String calculateFactorial(@PathVariable("n") int n, @RequestParam(value = "key", required = false) String key) {
        if (n>10) {
            if (!apiKey.equals(key)) {
                throw new IncorrectApiKeyException("To calculate more than 10 factorials, you need the correct api-key");
            }
        }

        BigDecimal result;
        BigDecimal cachedResult = cacheService.cachedFactorial(n);

        if (cachedResult!=null) {
            result = cachedResult;
        } else {
            if (n>1000) {
                long size = taskService.saveCalculationTask(n);
                return switch (language) {
                    case "ko" -> n + "! 계산이 예약되었습니다. 남은 작업 : " + size;
                    case "en" -> n + "! has been scheduled. Remain task : " + size;
                    default -> "Unsupported Language";
                };
            }
            result = calculateService.getCalculatedResult(n);
            cacheService.cacheFactorial(n, result);
        }

        return switch (language) {
            case "ko" -> n + " 팩토리얼은 " + result + " 입니다";
            case "en" -> "the factorial of " + n + " is " + result;
            default -> "Unsupported Language";
        };
    }
}