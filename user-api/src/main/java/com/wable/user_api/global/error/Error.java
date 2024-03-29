package com.wable.user_api.global.error;

import lombok.Getter;

@Getter
public enum Error {
    UPDATE_FORBIDDEN("수정 권한이 없습니다."),
    CARD_NOT_FOUND("카드를 찾을 수 없습니다."),
    INVALID_INPUT_VALUE("입력값이 올바르지 않습니다."),
    EMAIL_DUPLICATION("중복된 이메일입니다."),
    ACCOUNT_NOT_FOUND("계정을 찾을 수 없습니다."),
    INVALID_TOKEN("올바르지 않은 Token 입니다."),
    REFRESH_TOKEN_NOT_FOUND("이미 로그아웃된 사용자입니다."),
    PROFILE_DUPLICATION("이미 등록된 프로필입니다."),
    PROFILE_NOT_FOUND("프로필을 찾을 수 없습니다."),
    GROUP_DUPLICATION("이미 등록된 그룹입니다."),
    GROUP_NOT_FOUND("그룹을 찾을 수 없습니다."),
    GROUP_USER_NOT_FOUND("그룹에 소속된 유저가 아닙니다."),
    GROUP_NOT_OWNER("그룹의 소유자가 아닙니다."),

    POST_NOT_FOUND("게시글을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND("댓글을 찾을 수 없습니다."),

    POST_LIKE_ERROR("잘못된 좋아요 요청입니다."),
    GROUP_NAME_DUPLICATE("사용할 수 없는 그룹 이름입니다.");



    private final String message;

    Error(String message) {
        this.message = message;
    }
}
