package com.wable.harmonika.domain.group;

import lombok.Getter;

@Getter
public class PagingMemberRequest {
    private static final int DEFAULT_PAGING_SIZE = 10;
    private static final int MAX_PAGING_SIZE = 30;

    private String lastName;
    private Integer size;

    public PagingMemberRequest() {
    }

    public PagingMemberRequest(String lastName, Integer size) {
        this.lastName = lastName;
        this.size = size;
    }

    public int getSize() {
        if (size == null) {
            return DEFAULT_PAGING_SIZE;
        }
        if (size > MAX_PAGING_SIZE) {
            return MAX_PAGING_SIZE;
        }
        if (size <= 0) {
            return DEFAULT_PAGING_SIZE;
        }
        return size;
    }

}
