package com.wanted.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.global.code.FailCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {
    // 스프링 시큐리티 인증 불가 핸들러
    // 유효한 토큰이 없어 인증에 필요한 정보가 없는 경우 해당 핸들러가 응답
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        String accessToken = request.getHeader("Authorization");
        log.info("AuthenticationEntryPoint : 인증 불가 토큰 : {}", accessToken);
        SecurityResponse.setFailResponse(response, FailCode.UN_AUTHENTICATION);
    }
}