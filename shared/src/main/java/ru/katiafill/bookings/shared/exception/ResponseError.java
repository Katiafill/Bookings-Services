package ru.katiafill.bookings.shared.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class ResponseError {
    private String message;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> errors;

    public ResponseError(String message, Map<String, String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public ResponseError(String message) {
        this.message = message;
    }
}
