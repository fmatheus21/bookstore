package com.fmatheus.app.controller.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessages {
    private LocalDateTime timestamp = LocalDateTime.now();
    private int statusCode;
    private String statusDescription;
    private String cause;
    private String message;

    public ResponseMessages(HttpStatus httpStatus, String message) {
        this.statusCode = httpStatus.value();
        this.statusDescription = httpStatus.name();
        this.cause = httpStatus.getReasonPhrase();
        this.message = message;
    }
}
