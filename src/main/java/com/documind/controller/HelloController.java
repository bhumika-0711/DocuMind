
package com.documind.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public Map<String, Object> sayHello() {
        return Map.of(
                "message", "DocuMind backend is alive!",
                "timestamp", LocalDateTime.now().toString()
        );
    }
}