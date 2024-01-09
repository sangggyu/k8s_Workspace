package com.example.factorialcache.util;

import org.springframework.beans.factory.annotation.Value;import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

@Component
public class FileLogger {

    @Value("${HOSTNAME:UNRESOLVED_HOST}")
    private String host;

    public void log(String message) {

        try {
            Files.writeString(
                    Paths.get("/factorial/logs/cache-log.log"),
                    host + "::" + message + System.lineSeparator(),
                    CREATE, APPEND
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
