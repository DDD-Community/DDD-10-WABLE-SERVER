package com.wable.harmonika.global.error;

import com.wable.harmonika.domain.user.exception.EmailDuplicationException;
import com.wable.harmonika.domain.user.exception.UserNotFoundException;
import com.wable.harmonika.global.error.exception.InvalidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ForbiddenException;

import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final Error error = Error.INVALID_INPUT_VALUE;
        final List<ErrorResponse.FieldError> fieldErrors = getFieldErrorsByBindingResult(e.getBindingResult());
        return buildFieldErrors(error, fieldErrors);
    }

    // NOTE: 400 에러는 InvalidException 을 사용함
    @ExceptionHandler({InvalidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleInvalidException(InvalidException e) {
        log.error("{} {} : {}", e.getMessage(), e.getField(), e.getValue());
        List<ErrorResponse.FieldError> fieldError = getFieldError(e.getField(), e.getValue(), e.getMessage());
        return ErrorResponse.builder()
                .message(e.getMessage())
                .detail(fieldError)
                .build();
    }

    @ExceptionHandler({AccountNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleAccountNotFoundException(UserNotFoundException e) {
        final Error error = Error.ACCOUNT_NOT_FOUND;
        log.error("{} {} : {}", error.getMessage(), e.getField(), e.getValue());
        List<ErrorResponse.FieldError> fieldError = getFieldError(e.getField(), e.getValue());
        return buildFieldErrors(error, fieldError);
    }

    @ExceptionHandler(EmailDuplicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleConstraintViolationException(EmailDuplicationException e) {
        final Error error = Error.EMAIL_DUPLICATION;
        log.error("{} {} : {}", error.getMessage(), e.getField(), e.getValue());
        List<ErrorResponse.FieldError> fieldError = getFieldError(e.getField(), e.getValue());
        return buildFieldErrors(error, fieldError);
    }

    private ErrorResponse buildError(Error error) {
        ErrorResponse retError = ErrorResponse.builder()
                .message(error.getMessage())
                .build();
        return retError;
    }

    private List<ErrorResponse.FieldError> getFieldErrorsByBindingResult(BindingResult bindingResult) {
        final List<FieldError> detail = bindingResult.getFieldErrors();
        return detail.parallelStream()
                .map(error -> ErrorResponse.FieldError.builder()
                        .message(error.getDefaultMessage())
                        .field(error.getField())
                        .value(error.getRejectedValue())
                        .build())
                .collect(Collectors.toList());
    }

    private List<ErrorResponse.FieldError> getFieldError(String field, Object value) {
        List<ErrorResponse.FieldError> fieldError =  new ArrayList<>(Arrays.asList(ErrorResponse.FieldError.builder()
                .field(field)
                .value(value)
                .build()));
        return fieldError;
    }

    private List<ErrorResponse.FieldError> getFieldError(String field, Object value, String message) {
        List<ErrorResponse.FieldError> fieldError =  new ArrayList<>(Arrays.asList(ErrorResponse.FieldError.builder()
                .field(field)
                .value(value)
                .message(message)
                .build()));
        return fieldError;
    }


    private ErrorResponse buildFieldErrors(Error error, List<ErrorResponse.FieldError> detail) {
        return ErrorResponse.builder()
                .message(error.getMessage())
                .detail(detail)
                .build();
    }
}
