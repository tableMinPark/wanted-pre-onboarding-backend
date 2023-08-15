package com.wanted.auth.controller;

import com.wanted.auth.dto.response.LoginMemberResDto;
import com.wanted.auth.entity.Member;
import com.wanted.auth.repository.MemberRepository;
import com.wanted.auth.service.AuthService;
import com.wanted.global.code.RoleCode;
import com.wanted.global.redis.AccessTokenRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginMemberTest {
    @Autowired
    private AuthService authService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AccessTokenRepository accessTokenRepository;


    @DisplayName("로그인 테스트")
    @Test
    @Transactional
    void loginMemberTest() {
        String email = "test@test.com";
        String password = "12345678";

        String encodePassword = passwordEncoder.encode(password);
        Member member = Member.builder()
                .email(email)
                .password(encodePassword)
                .role(RoleCode.USER.code)
                .build();
        memberRepository.save(member);

        try {
            authService.loginMember(email, password);
        } catch (RuntimeException e) {
            e.printStackTrace();
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
            authService.loginMember(email, password);
        } catch (RuntimeException e) {
            e.printStackTrace();
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
            authService.loginMember(email, password);
        } catch (RuntimeException e) {
            e.printStackTrace();
            assertTrue(true);
            return;
        }
        fail();
    }

    @DisplayName("비밀번호 일치 검증 테스트")
    @Test
    @Transactional
    void collectPasswordTest() {
        String email = "test@test.com";
        String password = "12345678";

        String encodePassword = passwordEncoder.encode(password);
        Member member = Member.builder()
                .email(email)
                .password(encodePassword)
                .role(RoleCode.USER.code)
                .build();
        memberRepository.save(member);

        password = "11111111";

        try {
            authService.loginMember(email, password);
        } catch (RuntimeException e) {
            e.printStackTrace();
            assertTrue(true);
            return;
        }
        fail();
    }

    @DisplayName("이메일, 비밀번호 미입력 테스트")
    @Test
    @Transactional
    void invalidArgsTest1() {
        String email = "";
        String password = "";

        try {
            authService.loginMember(email, password);
        } catch (RuntimeException e) {
            e.printStackTrace();
            assertTrue(true);
            return;
        }
        fail();

    }

    @DisplayName("이메일 미입력 테스트")
    @Test
    @Transactional
    void invalidArgsTest2() {
        String email = "";
        String password = "12345678";

        try {
            authService.loginMember(email, password);
        } catch (RuntimeException e) {
            e.printStackTrace();
            assertTrue(true);
            return;
        }
        fail();

    }

    @DisplayName("비밀번호 미입력 테스트")
    @Test
    @Transactional
    void invalidArgsTest3() {
        String email = "test@test.com";
        String password = "";

        try {
            authService.loginMember(email, password);
        } catch (RuntimeException e) {
            e.printStackTrace();
            assertTrue(true);
            return;
        }
        fail();
    }

    @DisplayName("회원 존재 여부 확인 테스트")
    @Test
    @Transactional
    void existMemberTest() {
        String email = "test@test.com";
        String password = "12345678";

        try {
            authService.loginMember(email, password);
        } catch (RuntimeException e) {
            e.printStackTrace();
            assertTrue(true);
            return;
        }
        fail();
    }

    @DisplayName("엑세스 토큰 재사용 테스트")
    @Test
    @Transactional
    void recycleTokenTest() {
        String email = "test@test.com";
        String password = "12345678";

        String encodePassword = passwordEncoder.encode(password);
        Member member = Member.builder()
                .email(email)
                .password(encodePassword)
                .role(RoleCode.USER.code)
                .build();
        memberRepository.save(member);

        LoginMemberResDto first = authService.loginMember(email, password);
        LoginMemberResDto second = authService.loginMember(email, password);

        // 첫 번째 호출했을 때의 토큰 값과 두 번째 호출했을 때의 토큰 값이 같은지 확인
        assertEquals(first.getAccessToken(), second.getAccessToken());

        accessTokenRepository.deleteByMemberId(member.getMemberId());
    }
}