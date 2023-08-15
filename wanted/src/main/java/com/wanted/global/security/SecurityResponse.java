package com.wanted.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.global.code.BasicFailCode;
import com.wanted.global.code.ErrorCode;
import com.wanted.global.response.ErrorResponse;
import com.wanted.global.response.FailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityResponse {
    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    public static void setFailResponse(HttpServletResponse response, BasicFailCode failCode) {
        response.setContentType("application/json; charset=UTF-8");

        try {
            response.setStatus(failCode.getHttpStatus().value());
            response.getWriter().write(objectMapper.writeValueAsString(new FailResponse(failCode)));
        } catch (IOException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public static void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) {
        response.setContentType("application/json; charset=UTF-8");

        try {
            response.setStatus(errorCode.getHttpStatus().value());
            response.getWriter().write(objectMapper.writeValueAsString(new ErrorResponse(errorCode)));
        } catch (IOException e){
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
