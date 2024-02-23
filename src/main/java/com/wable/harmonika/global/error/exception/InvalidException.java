package com.wable.harmonika.global.error.exception;

import com.wable.harmonika.global.error.Error;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidException extends EntityNotFoundException {
    private String field;
    private Object value;
    private String message;

    public InvalidException(String field, Object value, Error message) {
        this.field = field;
        this.value = value;
        this.message = message.getMessage();
    }
}
