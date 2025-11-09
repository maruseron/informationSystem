package com.maruseron.informationSystem.util;

import org.springframework.http.HttpStatus;

public record HttpResult(HttpStatus status, String message) {
    public HttpResult(HttpStatus status) {
        this(status, "");
    }
}
