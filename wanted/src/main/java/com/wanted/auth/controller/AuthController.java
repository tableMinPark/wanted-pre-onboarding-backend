package com.wanted.auth.controller;

import com.wanted.auth.dto.request.LoginMemberReqDto;
import com.wanted.auth.dto.request.RegisterMemberReqDto;
import com.wanted.auth.dto.response.LoginMemberResDto;
import com.wanted.auth.service.AuthService;
import com.wanted.global.code.FailCode;
import com.wanted.global.exception.fail.NotFoundException;
import com.wanted.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    private ResponseEntity<Object> registerMember(RegisterMemberReqDto registerMemberReqDto) {
        log.info("registerMember - Call");

        String email = registerMemberReqDto.getEmail();
        String password = registerMemberReqDto.getPassword();

        if (email == null || password == null) {
            throw new NotFoundException(FailCode.INVALID_ARGS);
        }

        authService.registerMember(email, password);
        return ResponseEntity.ok().body(new SuccessResponse(null));
    }

    @PostMapping("/login")
    private ResponseEntity<Object> loginMember(LoginMemberReqDto loginMemberReqDto) {
        log.info("loginMember - Call");

        String email = loginMemberReqDto.getEmail();
        String password = loginMemberReqDto.getPassword();

        if (email == null || password == null) {
            throw new NotFoundException(FailCode.INVALID_ARGS);
        }

        LoginMemberResDto loginMemberResDto = authService.loginMember(email, password);
        return ResponseEntity.ok().body(new SuccessResponse(loginMemberResDto));
    }
}
