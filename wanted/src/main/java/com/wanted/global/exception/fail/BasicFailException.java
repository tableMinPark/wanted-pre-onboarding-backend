package com.wanted.global.exception.fail;

import com.wanted.global.code.BasicFailCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BasicFailException extends RuntimeException {
    protected final BasicFailCode code;

    public BasicFailCode getFailCode() {
        return this.code;
    }
}
