package com.mddvc.spring.app.exception;

import java.util.UUID;

public class MatchNotFoundException extends RuntimeException {
    public MatchNotFoundException(UUID uuid) {
        super("Ongoing match with %s id not found".formatted(uuid.toString()));
    }
}
