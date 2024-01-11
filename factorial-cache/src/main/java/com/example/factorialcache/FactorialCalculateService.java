package com.example.factorialcache;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@Service
public class FactorialCalculateService {

    private final RestClient factorialClient = RestClient.create();
    public BigDecimal getCalculatedResult(int n) {
        String  result = factorialClient.get()
                .uri("http://factorial-app-service:8080/factorial?n="+n)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    throw new RuntimeException("invalid server response " + response.getStatusText());
                })
                .body(String.class);

        return new BigDecimal(result);
    }
}
