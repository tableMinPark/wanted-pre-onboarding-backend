package com.wanted.global.response;

import com.wanted.global.code.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse extends BasicResponse {
    private String message;
    private Integer code;

    public ErrorResponse(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public ErrorResponse(ErrorCode errorCode){
        super("error");
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }
}
