package org.example.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class GeneralNoContentException extends RuntimeException {
    public GeneralNoContentException() {
        super();
    }

    public GeneralNoContentException(String message) {
        super(message);
    }

    public GeneralNoContentException(Throwable cause) {
        super(cause);
    }
}
