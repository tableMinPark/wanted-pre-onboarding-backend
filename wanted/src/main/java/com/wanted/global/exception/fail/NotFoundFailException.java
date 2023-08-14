package com.wanted.global.exception.fail;

import com.wanted.global.code.BasicFailCode;

public class NotFoundFailException extends BasicFailException {
    public NotFoundFailException(BasicFailCode basicFailCode) {
        super(basicFailCode);
    }
}
