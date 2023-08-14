package com.wanted.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러", 500),
    NOT_REGISTER(HttpStatus.INTERNAL_SERVER_ERROR,"데이터 등록 오류", 501),
    NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR,"데이터 조회 오류", 502),
    NOT_MODIFY(HttpStatus.INTERNAL_SERVER_ERROR,"데이터 수정 오류", 503),
    NOT_DELETE(HttpStatus.INTERNAL_SERVER_ERROR,"데이터 삭제 오류", 504);

    public final HttpStatus httpStatus;
    public final String message;
    public final Integer code;
}
