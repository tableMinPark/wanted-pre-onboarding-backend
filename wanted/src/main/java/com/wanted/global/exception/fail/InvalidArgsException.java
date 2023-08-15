package com.wanted.global.exception.fail;

import com.wanted.global.code.BasicFailCode;

public class InvalidArgsException extends BasicFailException {
    public InvalidArgsException(BasicFailCode basicFailCode) {
        super(basicFailCode);
    }
}
