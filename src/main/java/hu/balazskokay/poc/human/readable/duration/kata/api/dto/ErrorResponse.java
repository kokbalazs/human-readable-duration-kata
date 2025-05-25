package hu.balazskokay.poc.human.readable.duration.kata.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {

    private final int status;
    private final String message;
    private final LocalDateTime timestamp = LocalDateTime.now();

}
