package ru.javaops.bootjava.web;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.javaops.bootjava.error.AppException;
import ru.javaops.bootjava.error.VoteTimeConstraintException;
import ru.javaops.bootjava.validation.ValidationUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> appException(AppException ex, WebRequest request) {
        log.error("Application Exception", ex);
        return createProblemDetailExceptionResponse(ex, ex.getStatusCode(), request);
    }

    @Override
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail body = ex.updateAndGetBody(this.messageSource, LocaleContextHolder.getLocale());
        Map<String, String> invalidParams = new LinkedHashMap<>();
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            invalidParams.put(error.getObjectName(), getErrorMessage(error));
        }
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            invalidParams.put(error.getField(), getErrorMessage(error));
        }
        body.setProperty("invalid_params", invalidParams);
        body.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        return handleExceptionInternal(ex, body, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<?> entityNotFoundException(WebRequest request, EntityNotFoundException ex) {
        log.error("EntityNotFoundException: {}", ex.getMessage());
        return createProblemDetailExceptionResponse(ex, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(VoteTimeConstraintException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<?> voteTimeConstraintException(WebRequest request, VoteTimeConstraintException ex) {
        log.error("VoteTimeConstraintException: {}", ex.getMessage());
        return createProblemDetailExceptionResponse(ex, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleConstraintViolationException(WebRequest request, ConstraintViolationException ex) {
        ProblemDetail body = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        Map<String, String> invalidParams = new LinkedHashMap<>();
        for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
            invalidParams.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
        }
        body.setProperty("invalid_params", invalidParams);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<?> conflict(WebRequest request, DataIntegrityViolationException ex) {
        log.error("DataIntegrityViolationException: {}", ex.getMessage());
        String msg = null;
        if (ValidationUtils.getRootCause(ex).getMessage().toLowerCase().contains("email_unique_idx")) {
            msg = "User with this email already exists";
        }
        if (ValidationUtils.getRootCause(ex).getMessage().toLowerCase().contains("uc_menu_date_of_menu")) {
            msg = "Menu for this restaurant on this date already exists";
        }
        if (ValidationUtils.getRootCause(ex).getMessage().toLowerCase().contains("foreign key(restaurant_id)")) {
            msg = "Wrong id for restaurant";
        }
        return createProblemDetailExceptionResponse(ex, HttpStatus.CONFLICT, request, msg);
    }

    private ResponseEntity<?> createProblemDetailExceptionResponse(Exception ex, HttpStatusCode statusCode, WebRequest request) {
        return createProblemDetailExceptionResponse(ex, statusCode, request, null);
    }

    private ResponseEntity<?> createProblemDetailExceptionResponse(Exception ex, HttpStatusCode statusCode, WebRequest request, @Nullable String customMessage) {
        String msg = customMessage != null ? customMessage : ex.getMessage();
        ProblemDetail body = createProblemDetail(ex, statusCode, msg, null, null, request);
        return handleExceptionInternal(ex, body, new HttpHeaders(), statusCode, request);
    }

    private String getErrorMessage(ObjectError error) {
        return messageSource.getMessage(Objects.requireNonNull(error.getCode()), error.getArguments(), error.getDefaultMessage(), LocaleContextHolder.getLocale());
    }
}
