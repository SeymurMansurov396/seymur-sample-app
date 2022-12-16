package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class JokeNotFoundException extends RuntimeException {
    public JokeNotFoundException() {
        super();
    }

    public JokeNotFoundException(String message) {
        super(message);
    }

    public JokeNotFoundException(Throwable cause) {
        super(cause);
    }
}
