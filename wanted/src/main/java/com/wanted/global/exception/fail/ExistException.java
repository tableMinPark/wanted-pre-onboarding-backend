package com.wanted.global.exception.fail;

import com.wanted.global.code.BasicFailCode;

public class ExistException extends BasicFailException {
    public ExistException(BasicFailCode basicFailCode) {
        super(basicFailCode);
    }
}
