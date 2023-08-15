package com.wanted.auth.service;

import com.wanted.auth.entity.Member;
import com.wanted.auth.repository.MemberRepository;
import com.wanted.global.code.RoleCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

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