package com.wable.harmonika.domain.profile.dto;

import com.wable.harmonika.domain.user.entity.Users;

public class SignedUrlDto {
    private String signedUrl;

    public SignedUrlDto(String signedUrl) {
        this.signedUrl = signedUrl;
    }
}
