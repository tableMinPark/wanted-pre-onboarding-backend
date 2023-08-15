package com.wanted.auth.service;

import com.wanted.auth.entity.Member;
import com.wanted.auth.repository.MemberRepository;
import com.wanted.global.code.FailCode;
import com.wanted.global.code.RoleCode;
import com.wanted.global.exception.fail.ExistException;
import com.wanted.global.exception.fail.InvalidArgsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerMember(String email, String password) throws RuntimeException {
        // 이메일 형식 검증
        if (!emailValidation(email)) {
            throw new InvalidArgsException(FailCode.INVALID_EMAIL);
        }
        // 비밀번호 형식 검증
        else if (!passwordValidation(password)) {
            throw new InvalidArgsException(FailCode.INVALID_PASSWORD);
        }
        // 회원 등록 여부 확인
        else if (memberRepository.findByEmail(email).isPresent()) {
            throw new ExistException(FailCode.EXIST_MEMBER);
        }

        // 비밀번호 암호화
        String encodePassword = passwordEncoder.encode(password);
        Member member = Member.builder()
                .email(email)
                .password(encodePassword)
                .role(RoleCode.USER.code)
                .build();

        memberRepository.save(member);
    }

    private boolean emailValidation(String email) {
        boolean isOk = true;

        // 이메일에 '@' 포함 여부 확인
        if (!email.contains("@")) {
            isOk = false;
        }

        return isOk;
    }

    private boolean passwordValidation(String password) {
        boolean isOk = true;

        // 비밀번호 8자 이상 여부 확인
        if (password.length() < 8) {
            isOk = false;
        }

        return isOk;
    }
}
