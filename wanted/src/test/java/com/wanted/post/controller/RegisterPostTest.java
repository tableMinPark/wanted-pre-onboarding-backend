package com.wanted.post.controller;

import com.wanted.auth.entity.Member;
import com.wanted.auth.repository.MemberRepository;
import com.wanted.global.code.RoleCode;
import com.wanted.post.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegisterPostTest {
    @Autowired
    private PostService postService;
    @Autowired
    private MemberRepository memberRepository;

    Long registerMember() {
        String email = "test@test.com";
        String password = "12345678";

        Member member = Member.builder()
                .email(email)
                .password(password)
                .role(RoleCode.USER.code)
                .build();
        memberRepository.save(member);

        return member.getMemberId();
    }

    @DisplayName("게시물 등록 테스트")
    @Test
    @Transactional
    void registerPostTest() {
        Long memberId = registerMember();
        String title = "제목 테스트";
        String content = "본문 테스트";

        try {
            postService.registerPost(memberId, title, content);
        } catch (RuntimeException e) {
            e.printStackTrace();
            fail();
        }
    }

    @DisplayName("제목, 본문 미입력 테스트")
    @Test
    @Transactional
    void invalidArgsTest() {
        Long memberId = registerMember();
        String title = "";
        String content = "";

        try {
            postService.registerPost(memberId, title, content);
        } catch (RuntimeException e) {
            e.printStackTrace();
            assertTrue(true);
            return;
        }
        fail();
    }

    @DisplayName("제목 미입력 테스트")
    @Test
    @Transactional
    void invalidArgsTest2() {
        Long memberId = registerMember();
        String title = "";
        String content = "본문 테스트";

        try {
            postService.registerPost(memberId, title, content);
        } catch (RuntimeException e) {
            e.printStackTrace();
            assertTrue(true);
            return;
        }
        fail();
    }

    @DisplayName("본문 미입력 테스트")
    @Test
    @Transactional
    void invalidArgsTest3() {
        Long memberId = registerMember();
        String title = "제목 테스트";
        String content = "";

        try {
            postService.registerPost(memberId, title, content);
        } catch (RuntimeException e) {
            e.printStackTrace();
            assertTrue(true);
            return;
        }
        fail();
    }
}