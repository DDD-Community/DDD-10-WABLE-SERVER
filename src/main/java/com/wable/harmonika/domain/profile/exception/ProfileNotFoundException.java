package com.wable.harmonika.domain.profile.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;

@Getter
public class ProfileNotFoundException extends EntityNotFoundException {
    private String field;
    private Object value;

    public ProfileNotFoundException(String field, Object value) {
        this.field = field;
        this.value = value;
    }
}
