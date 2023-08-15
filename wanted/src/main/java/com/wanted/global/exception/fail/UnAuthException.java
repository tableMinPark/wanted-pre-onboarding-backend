package com.wanted.global.exception.fail;

import com.wanted.global.code.BasicFailCode;

public class UnAuthException extends BasicFailException {
    public UnAuthException(BasicFailCode basicFailCode) {
        super(basicFailCode);
    }
}
