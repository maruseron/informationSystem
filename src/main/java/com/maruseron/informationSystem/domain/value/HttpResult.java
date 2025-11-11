package com.maruseron.informationSystem.domain.value;

import org.springframework.http.HttpStatus;

public record HttpResult(HttpStatus status, String message) {
    public HttpResult(HttpStatus status) {
        this(status, "");
    }
}
