package com.wanted.global.security;

import com.wanted.global.code.FailCode;
import com.wanted.global.exception.fail.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
public class AuthoritiesUtil {
    public static Long getMemberId() throws NotFoundException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
            return customUserDetail.getMemberId();
        } catch (ClassCastException e) {
            throw new NotFoundException(FailCode.NOT_FOUND_AUTHORITIES);
        }
    }
}
