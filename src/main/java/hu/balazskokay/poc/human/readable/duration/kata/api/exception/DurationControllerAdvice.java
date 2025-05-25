package hu.balazskokay.poc.human.readable.duration.kata.api.exception;

import hu.balazskokay.poc.human.readable.duration.kata.api.dto.ErrorResponse;
import hu.balazskokay.poc.human.readable.duration.kata.core.port.in.NegativeNumberException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class DurationControllerAdvice {

    @ExceptionHandler(NegativeNumberException.class)
    public ResponseEntity<ErrorResponse> handleNegativeNumberException() {
        return new ResponseEntity<>(
                new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "Negative numbers are not allowed for duration."
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException() {
        return new ResponseEntity<>(
                new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "Invalid input type for duration. Please provide a valid integer."
                ),
                HttpStatus.BAD_REQUEST
        );
    }

}
