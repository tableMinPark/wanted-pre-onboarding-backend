package com.wanted.global.exception.fail;

import com.wanted.global.code.BasicFailCode;

public class NotFoundException extends BasicFailException {
    public NotFoundException(BasicFailCode basicFailCode) {
        super(basicFailCode);
    }
}
