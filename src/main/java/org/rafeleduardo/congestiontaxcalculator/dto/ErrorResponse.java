package org.rafeleduardo.congestiontaxcalculator.dto;

import javax.lang.model.type.ErrorType;
import java.time.LocalDateTime;

public record ErrorResponse(String message, LocalDateTime timestamp) {
    public ErrorResponse(String message) {
        this(message, LocalDateTime.now());
    }
}
