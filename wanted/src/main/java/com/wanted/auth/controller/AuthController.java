package com.wanted.auth.controller;

import com.wanted.auth.dto.request.LoginMemberReqDto;
import com.wanted.auth.dto.request.RegisterMemberReqDto;
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
    @PostMapping("/register")
    private ResponseEntity<Object> registerMember(RegisterMemberReqDto registerMemberReqDto) {
        log.info("registerMember - Call");
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/login")
    private ResponseEntity<Object> loginMember(LoginMemberReqDto loginMemberReqDto) {
        log.info("loginMember - Call");
        return ResponseEntity.ok().body(null);
    }
}
