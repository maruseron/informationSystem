package com.maruseron.informationSystem.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ResponseEntities {
    private ResponseEntities() {}

    // 400 BAD REQUEST
    public static ResponseEntity<String> badRequest(final String message) {
        return ResponseEntity.badRequest().body(message);
    }

    // 409 CONFLICT
    public static ResponseEntity<String> conflict(final String message) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }
}
