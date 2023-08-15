package com.wanted.auth.service;

import com.wanted.auth.entity.Member;
import com.wanted.auth.repository.MemberRepository;
import com.wanted.global.code.RoleCode;
import com.wanted.global.redis.AccessToken;
import com.wanted.global.redis.AccessTokenRepository;
import com.wanted.global.security.TokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @DisplayName("회원가입 테스트")
    @Test
    @Transactional
    void registerMemberTest() {
        String email = "test@test.com";
        String password = "12345678";

        try {
            // 이메일 형식 검증
            if (!emailValidation(email)) {
                throw new RuntimeException();
            }
            // 비밀번호 형식 검증
            else if (!passwordValidation(password)) {
                throw new RuntimeException();
            }

            else if (memberRepository.findByEmail(email).isPresent()) {
                throw new RuntimeException();
            }

            String encodePassword = passwordEncoder.encode(password);
            Member member = Member.builder()
                    .email(email)
                    .password(encodePassword)
                    .role(RoleCode.USER.code)
                    .build();
            memberRepository.save(member);

            // 비밀번호 암호화 확인
            assertTrue(passwordEncoder.matches(password, encodePassword));
            // 저장 확인
            memberRepository.findByEmail(email)
                    .orElseThrow(RuntimeException::new);

        } catch (RuntimeException e) {
            fail();
        }
    }

    @DisplayName("로그인 테스트")
    @Test
    @Transactional
    void loginMemberTest() {
        String email = "test@test.com";
        String password = "12345678";

        String encodePassword = passwordEncoder.encode(password);
        Member registerMember = Member.builder()
                .email(email)
                .password(encodePassword)
                .role(RoleCode.USER.code)
                .build();
        memberRepository.save(registerMember);

        try {
            // 이메일 형식 검증
            if (!emailValidation(email)) {
                throw new RuntimeException();
            }
            // 비밀번호 형식 검증
            else if (!passwordValidation(password)) {
                throw new RuntimeException();
            }

            // 회원 존재 여부 검증과 동시에 회원 조회
            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(RuntimeException::new);

            String memberPassword = member.getPassword();
            if (!passwordEncoder.matches(password, memberPassword)) {
                throw new RuntimeException();
            }

            // 엑세스 토큰 존재 여부 확인하고 없으면 토큰 생성 및 Redis 에 저장
            Long memberId = member.getMemberId();
            String role = member.getRole();
            AccessToken accessToken = accessTokenRepository.findByMemberId(member.getMemberId())
                    .orElseGet(() -> {
                        AccessToken token = tokenProvider.generateAccessToken(memberId, role);
                        accessTokenRepository.save(memberId, token);
                        return token;
                    });

            assertNotNull(accessToken);

        } catch (RuntimeException e) {
            fail();
        }
    }

    private boolean emailValidation(String email) {
        boolean isOk = true;

        // @ 포함
        if (!email.contains("@")) {
            isOk = false;
        }

        return isOk;
    }

    private boolean passwordValidation(String password) {
        boolean isOk = true;

        // 8자 이상
        if (password.length() < 8) {
            isOk = false;
        }

        return isOk;
    }
}