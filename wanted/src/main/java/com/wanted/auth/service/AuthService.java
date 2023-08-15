package com.wanted.auth.service;

import com.wanted.auth.dto.response.LoginMemberResDto;
import com.wanted.auth.entity.Member;
import com.wanted.auth.repository.MemberRepository;
import com.wanted.global.code.FailCode;
import com.wanted.global.code.RoleCode;
import com.wanted.global.exception.fail.ExistException;
import com.wanted.global.exception.fail.InvalidArgsException;
import com.wanted.global.exception.fail.NotFoundException;
import com.wanted.global.redis.AccessToken;
import com.wanted.global.redis.AccessTokenRepository;
import com.wanted.global.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AccessTokenRepository accessTokenRepository;

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

    public LoginMemberResDto loginMember(String email, String password) {
        // 이메일 형식 검증
        if (!emailValidation(email)) {
            throw new InvalidArgsException(FailCode.INVALID_EMAIL);
        }
        // 비밀번호 형식 검증
        else if (!passwordValidation(password)) {
            throw new InvalidArgsException(FailCode.INVALID_PASSWORD);
        }

        // 회원 존재 여부 검증과 동시에 회원 조회
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(FailCode.NOT_FOUND_MEMBER));

        String memberPassword = member.getPassword();
        if (!passwordEncoder.matches(password, memberPassword)) {
            throw new InvalidArgsException(FailCode.INVALID_PASSWORD);
        }

        // 엑세스 토큰 존재 여부 확인하고 없으면 토큰 생성
        Long memberId = member.getMemberId();
        String role = member.getRole();
        AccessToken accessToken = accessTokenRepository.findByMemberId(member.getMemberId())
                .orElseGet(() -> {
                    AccessToken token = tokenProvider.generateAccessToken(memberId, role);
                    accessTokenRepository.save(memberId, token);
                    return token;
                });

        return LoginMemberResDto.builder()
                .accessToken(accessToken.getAccessToken())
                .build();
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
