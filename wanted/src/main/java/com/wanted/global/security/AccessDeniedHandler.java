package com.wanted.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.global.code.FailCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {
    // 스프링 시큐리티 인가 실패 핸들러
    // 접근 권한이 없어 인가가 불가한 경우에 해당 핸들러가 응답
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        String accessToken = request.getHeader("Authorization");
        log.info("AuthenticationEntryPoint : 인가 불가 토큰 : {}", accessToken);
        SecurityResponse.setFailResponse(response, FailCode.FORBIDDEN);
    }
}
