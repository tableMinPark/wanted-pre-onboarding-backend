package com.wanted.auth.controller;

import com.wanted.global.code.ErrorCode;
import com.wanted.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AuthControllerAdvice {
    @ExceptionHandler({ RuntimeException.class })
    private ResponseEntity<Object> handleRuntimeException(RuntimeException e) {
        log.info("handleRuntimeException : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ErrorCode.INTERNAL_SERVER));
    }
}
