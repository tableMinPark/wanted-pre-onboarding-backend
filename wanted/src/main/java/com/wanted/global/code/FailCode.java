package com.wanted.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FailCode implements BasicFailCode {
    // Basic
    INVALID_ARGS(HttpStatus.BAD_REQUEST, "잘못된 요청", "잘못된 입력 값입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "잘못된 요청", "잘못된 URI 요청입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없음", "현재 권한으로는 접근할 수 없습니다."),
    UN_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "인증 불가", "회원 인증을 할 수 없습니다."),
    // Auth
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "잘못된 요청", "잘못된 이메일 값입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 요청", "잘못된 비밀번호 값입니다."),
    EXIST_MEMBER(HttpStatus.BAD_REQUEST, "이미 등록된 회원", "이메일, 비밀번호와 일치하는 회원 정보가 존재합니다."),
    NOT_FOUND_MEMBER(HttpStatus.UNAUTHORIZED, "등록되지 않은 회원", "이메일, 비밀번호와 일치하는 회원 정보가 없습니다."),
    // Post
    INVALID_TITLE(HttpStatus.BAD_REQUEST, "잘못된 요청", "title 값이 비어있습니다."),
    INVALID_CONTENT(HttpStatus.BAD_REQUEST, "잘못된 요청", "content 값이 비어있습니다."),
    INVALID_PAGE(HttpStatus.BAD_REQUEST, "잘못된 요청", "page 값이 1 이상이여야 합니다."),
    NOT_FOUND_POST(HttpStatus.BAD_REQUEST, "잘못된 요청", "post id 와 일치하는 게시물이 없습니다."),
    UN_AUTHENTICATION_POST(HttpStatus.UNAUTHORIZED, "잘못된 요청", "해당 게시물을 수정 및 삭제할 수 없습니다."),
    // Spring Security
    NOT_FOUND_AUTHORITIES(HttpStatus.NOT_FOUND, "인가 정보가 없음", "인가 정보가 없어서 회원 ID 를 찾을 수 없습니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "엑세스 토큰 만료", "액세스 토큰이 만료되었습니다.");

    public final HttpStatus httpStatus;
    public final String title;
    public final String content;
}
