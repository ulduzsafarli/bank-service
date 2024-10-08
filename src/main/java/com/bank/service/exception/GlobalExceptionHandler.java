package com.bank.service.exception;

import com.bank.service.exception.custom.DuplicateDataException;
import com.bank.service.exception.custom.EmailSendingException;
import com.bank.service.exception.custom.FetchingDataException;
import com.bank.service.exception.custom.NotFoundException;
import com.bank.service.exception.error.ErrorDetails;
import com.bank.service.exception.error.ValidationError;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.micrometer.common.lang.NonNull;
import jakarta.mail.MessagingException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import com.bank.service.exception.custom.*;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String EXCEPTION_OCCURRED_MESSAGE = "Exception occurred";

    @ExceptionHandler
    public ResponseEntity<ErrorDetails> handle(
            MessagingException ex, WebRequest webRequest) {
        log.error(EXCEPTION_OCCURRED_MESSAGE, ex);
        return new ResponseEntity<>(createErrorDetails(ex, webRequest, HttpStatus.CONFLICT), HttpStatus.CONFLICT);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorDetails> handle(
            IllegalStateException ex, WebRequest webRequest) {
        log.error(EXCEPTION_OCCURRED_MESSAGE, ex);
        return new ResponseEntity<>(createErrorDetails(ex, webRequest, HttpStatus.CONFLICT), HttpStatus.CONFLICT);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorDetails> handle(
            BadCredentialsException ex, WebRequest webRequest) {
        log.error(EXCEPTION_OCCURRED_MESSAGE, ex);
        return new ResponseEntity<>(createErrorDetails(ex, webRequest, HttpStatus.CONFLICT), HttpStatus.CONFLICT);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorDetails> handle(
            FetchingDataException ex, WebRequest webRequest) {
        log.error(EXCEPTION_OCCURRED_MESSAGE, ex);
        return new ResponseEntity<>(createErrorDetails(ex, webRequest, HttpStatus.CONFLICT), HttpStatus.CONFLICT);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorDetails> handle(
            EmailSendingException ex, WebRequest webRequest) {
        log.error(EXCEPTION_OCCURRED_MESSAGE, ex);
        return new ResponseEntity<>(createErrorDetails(ex, webRequest, HttpStatus.CONFLICT), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDetails> handle(
            ConstraintViolationException ex, WebRequest webRequest) {
        log.error(EXCEPTION_OCCURRED_MESSAGE, ex);
        return new ResponseEntity<>(createErrorDetails(ex, webRequest, HttpStatus.CONFLICT), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDetails> handle(
            AuthenticationException ex, WebRequest webRequest) {
        log.error(EXCEPTION_OCCURRED_MESSAGE, ex);
        return new ResponseEntity<>(createErrorDetails(ex, webRequest, HttpStatus.CONFLICT), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDetails> handle(
            InvalidFormatException ex, WebRequest webRequest) {
        log.error(EXCEPTION_OCCURRED_MESSAGE, ex);

        return new ResponseEntity<>(createErrorDetails(ex, webRequest, HttpStatus.CONFLICT), HttpStatus.CONFLICT);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorDetails> handle(
            NotFoundException ex, WebRequest webRequest) {
        log.error(EXCEPTION_OCCURRED_MESSAGE, ex);
        return new ResponseEntity<>(createErrorDetails(ex, webRequest, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDetails> handle(
            DuplicateDataException ex, WebRequest webRequest) {
        log.error(EXCEPTION_OCCURRED_MESSAGE, ex);
        return new ResponseEntity<>(createErrorDetails(ex, webRequest, HttpStatus.CONFLICT), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handle(RuntimeException ex) {
        log.error(EXCEPTION_OCCURRED_MESSAGE, ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    private ErrorDetails createErrorDetails(
            Exception ex, WebRequest webRequest, HttpStatus status) {
        log.error("{} exception at path {}", ex.getClass().getSimpleName(),
                webRequest.getDescription(false), ex);

        List<ValidationError> validationErrors = new ArrayList<>();
        validationErrors.add(new ValidationError(ex.getMessage(), status.getReasonPhrase()));

        Map<String, List<ValidationError>> errorMap = new HashMap<>();
        errorMap.put("errors", validationErrors);

        return ErrorDetails.builder()
                .timeStamp(new Date())
                .status(status.value())
                .errors(errorMap)
                .detail(ex.getMessage())
                .developerMessage(ex.toString())
                .path(webRequest.getDescription(false))
                .build();
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @NonNull HttpMessageNotReadableException ex,
            @NonNull HttpHeaders headers,
            HttpStatusCode status,
            @NonNull WebRequest request) {

        log.error("{} exception at path {}", ex.getClass().getSimpleName(),
                request.getDescription(false), ex);

        ErrorDetails errorDetail = new ErrorDetails();
        errorDetail.setTimeStamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Message Not Readable");
        errorDetail.setDetail("The request body is not readable. Please check the request format.");
        errorDetail.setDeveloperMessage(ex.getClass().getName());

        return handleExceptionInternal(ex, errorDetail, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException mane,
            @NonNull HttpHeaders headers,
            HttpStatusCode status,
            @NonNull WebRequest request) {

        log.error("{} exception: {}", mane.getClass().getSimpleName(), mane.getMessage());

        ErrorDetails errorDetail = new ErrorDetails();
        errorDetail.setTimeStamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Argument Not Valid");

        List<FieldError> fieldErrors = mane.getBindingResult().getFieldErrors();
        for (FieldError fe : fieldErrors) {
            String fieldName = fe.getField();
            String errorMessage = fe.getDefaultMessage();
            errorDetail.addError(fieldName, errorMessage);
        }

        return handleExceptionInternal(mane, errorDetail, headers, status, request);
    }

}
