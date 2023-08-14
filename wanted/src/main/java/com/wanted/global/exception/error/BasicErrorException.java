package com.wanted.global.exception.error;

import com.wanted.global.code.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BasicErrorException extends RuntimeException {
    protected final ErrorCode code;

    public ErrorCode getFailCode() {
        return this.code;
    }
}
