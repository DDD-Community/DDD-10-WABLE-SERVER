package com.wable.harmonika.domain.group.entity;

import java.lang.reflect.Member;

public enum Position {
    OWNER, MEMBER;

    public static Position of(String position) {
        return Position.valueOf(position.toUpperCase());
    }

}
