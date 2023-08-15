package com.wanted.global.security;

import com.wanted.global.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenExceptionFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("TokenExceptionFilter : 서비스에 접근할 수 없음");
            SecurityResponse.setErrorResponse((HttpServletResponse) response, ErrorCode.INTERNAL_SERVER);
        }
    }
}
