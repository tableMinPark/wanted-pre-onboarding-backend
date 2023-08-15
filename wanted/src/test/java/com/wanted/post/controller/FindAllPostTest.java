package com.wanted.post.controller;

import com.wanted.auth.entity.Member;
import com.wanted.auth.repository.MemberRepository;
import com.wanted.global.code.RoleCode;
import com.wanted.post.dto.response.FindAllPostResDto;
import com.wanted.post.entity.Post;
import com.wanted.post.repository.PostRepository;
import com.wanted.post.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FindAllPostTest {
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
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

    void registerPostList(int length) {
        Long memberId = registerMember();

        for (int num = 0; num < length; num++) {
            String title = String.format("%d", num);
            String content = String.format("%d", num);

            Post post = Post.builder()
                    .title(title)
                    .content(content)
                    .member(Member.builder()
                            .memberId(memberId)
                            .build())
                    .build();
            postRepository.save(post);
        }
        postRepository.flush();
    }

    @DisplayName("게시물 목록 조회 테스트")
    @Test
    @Transactional
    void findAllPostTest() {
        registerPostList(10);

        int page = 1;
        int size = 5;
        int num = 10;

        List<FindAllPostResDto> findAllPostResDtoList = postService.findAllPost(page, size);
        // 검증
        for (int idx = 0; idx < 5; idx++) {
            assertEquals(String.valueOf(--num), findAllPostResDtoList.get(idx).getTitle());
        }

        page = 2;
        findAllPostResDtoList = postService.findAllPost(page, size);
        // 검증
        for (int idx = 0; idx < 5; idx++) {
            assertEquals(String.valueOf(--num), findAllPostResDtoList.get(idx).getTitle());
        }
    }

    @DisplayName("페이지 유효성 테스트")
    @Test
    @Transactional
    void invalidArgsTest() {
        int page = 0;
        int size = 5;

        try {
            postService.findAllPost(page, size);
        } catch (RuntimeException e) {
            assertTrue(true);
            return;
        }
        fail();
    }
}