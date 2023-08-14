package com.wanted.auth.repository;

import com.wanted.auth.entity.Member;
import com.wanted.global.code.RoleCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    private final String role = RoleCode.USER.code;

    @DisplayName("회원 저장 및 조회 테스트")
    @Test
    @Transactional
    void registerAndFindMemberTest() {
        String email = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();

        Member registerMember = Member.builder()
                .email(email)
                .password(password)
                .role(role)
                .build();
        memberRepository.save(registerMember);

        // 조회
        Optional<Member> member = memberRepository.findById(registerMember.getMemberId());
        assertTrue(member.isPresent());
    }

    @DisplayName("Member 수정 테스트")
    @Test
    @Transactional
    void modifyMemberTest() {
        String email = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();

        Member registerMember = Member.builder()
                .email(email)
                .password(password)
                .role(role)
                .build();
        memberRepository.save(registerMember);

        // 조회
        Long memberId = registerMember.getMemberId();
        Optional<Member> member = memberRepository.findById(memberId);
        assertTrue(member.isPresent());

        // 수정
        String modifyEmail = UUID.randomUUID().toString();
        Member modifyMember = member.get();
        modifyMember.setEmail(modifyEmail);
        memberRepository.save(modifyMember);

        // 수정 후 조회
        member = memberRepository.findById(memberId);
        assertTrue(member.isPresent());
        modifyMember = member.get();
        assertEquals(modifyEmail, modifyMember.getEmail());
    }

    @DisplayName("Member 삭제 테스트")
    @Test
    @Transactional
    void deleteMemberTest() {
        String email = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();

        Member registerMember = Member.builder()
                .email(email)
                .password(password)
                .role(role)
                .build();
        memberRepository.save(registerMember);

        Long memberId = registerMember.getMemberId();
        memberRepository.deleteById(memberId);

        Optional<Member> member = memberRepository.findById(memberId);
        assertFalse(member.isPresent());
    }
}