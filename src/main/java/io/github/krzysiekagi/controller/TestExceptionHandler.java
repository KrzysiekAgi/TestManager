package io.github.krzysiekagi.controller;

import io.github.krzysiekagi.exception.InvalidTestNameException;
import io.github.krzysiekagi.exception.TestNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TestExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = TestNotFoundException.class)
    ResponseEntity<String> handleTestNotFoundException(TestNotFoundException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    ResponseEntity<String> handleWrongTestStatusException(IllegalArgumentException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Disallowed test status value");
    }

    @ExceptionHandler(value = InvalidTestNameException.class)
    ResponseEntity<String> handleInvalidTestnameException(InvalidTestNameException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
