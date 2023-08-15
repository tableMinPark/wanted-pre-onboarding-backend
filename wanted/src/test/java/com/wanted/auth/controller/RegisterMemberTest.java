package com.wanted.auth.controller;

import com.wanted.auth.service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegisterMemberTest {
    @Autowired
    private AuthService authService;

    @DisplayName("회원 등록 테스트")
    @Test
    @Transactional
    void registerMemberTest() {
        String email = "test@test.com";
        String password = "12345678";

        try {
            authService.registerMember(email, password);
        } catch (RuntimeException e) {
            fail();
            return;
        }
        assertTrue(true);
    }

    @DisplayName("이메일 유효성 검증 테스트")
    @Test
    @Transactional
    void invalidEmailTest() {
        String email = "test test.com";
        String password = "12345678";

        try {
            authService.registerMember(email, password);
        } catch (RuntimeException e) {
            assertTrue(true);
            return;
        }
        fail();
    }

    @DisplayName("비밀번호 유효성 검증 테스트")
    @Test
    @Transactional
    void invalidPasswordTest() {
        String email = "test@test.com";
        String password = "1234";

        try {
            authService.registerMember(email, password);
        } catch (RuntimeException e) {
            assertTrue(true);
            return;
        }
        fail();
    }

    @DisplayName("이메일, 비밀번호 미입력 테스트")
    @Test
    @Transactional
    void invalidArgsTest1() {
        String email = null;
        String password = null;

        try {
            authService.registerMember(email, password);
        } catch (RuntimeException e) {
            assertTrue(true);
            return;
        }
        fail();
    }
    @DisplayName("이메일 미입력 테스트")
    @Test
    @Transactional
    void invalidArgsTest2() {
        String email = "test@test.com";
        String password = null;

        try {
            authService.registerMember(email, password);
        } catch (RuntimeException e) {
            assertTrue(true);
            return;
        }
        fail();
    }
    @DisplayName("비밀번호 미입력 테스트")
    @Test
    @Transactional
    void invalidArgsTest3() {
        String email = null;
        String password = "12345678";

        try {
            authService.registerMember(email, password);

        } catch (RuntimeException e) {
            assertTrue(true);
            return;
        }
        fail();
    }

    @DisplayName("중복 회원 등록 테스트")
    @Test
    @Transactional
    void duplicationMemberTest() {
        String email = "test@test.com";
        String password = "1234";

        try {
            authService.registerMember(email, password);
            authService.registerMember(email, password);
        } catch (RuntimeException e) {
            assertTrue(true);
            return;
        }
        fail();
    }
}