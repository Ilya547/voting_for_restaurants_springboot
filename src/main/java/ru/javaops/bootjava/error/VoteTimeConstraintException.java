package ru.javaops.bootjava.error;

import org.springframework.http.HttpStatus;

public class VoteTimeConstraintException extends AppException {
    public VoteTimeConstraintException(String msg) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, msg);
    }
}
