package ru.javaops.bootjava.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class AppException extends ResponseStatusException {
    public AppException(HttpStatus status, String msg) {
        super(status, msg);
    }

    @Override
    public String getMessage() {
        return getReason();
    }
}
