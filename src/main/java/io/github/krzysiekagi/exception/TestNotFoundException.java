package io.github.krzysiekagi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class TestNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public TestNotFoundException(Long id) {
        super(String.format("Test with Id %d not found", id));
    }
}
