package com.wable.harmonika.domain.user.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;

@Getter
public class UserNotFoundException extends EntityNotFoundException {
    private String field;
    private Object value;

    public UserNotFoundException(String field, Object value) {
        this.field = field;
        this.value = value;
    }
}
