package io.github.krzysiekagi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidTestNameException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidTestNameException(String name) {
        super(String.format("Name %s doesn't meet the demands (between 1 and 254 wordlike characters)", name));
    }
}
