package com.wanted.post.controller;

import com.wanted.auth.entity.Member;
import com.wanted.auth.repository.MemberRepository;
import com.wanted.global.code.RoleCode;
import com.wanted.post.entity.Post;
import com.wanted.post.repository.PostRepository;
import com.wanted.post.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeletePostTest {
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

    Long registerPost(Long memberId, String title, String content) {
        Post post = Post.builder()
                .title(title)
                .content(content)
                .member(Member.builder()
                        .memberId(memberId)
                        .build())
                .build();
        postRepository.save(post);
        postRepository.flush();

        return post.getPostId();
    }

    @DisplayName("게시물 삭제 테스트")
    @Test
    @Transactional
    void deletePostTest() {
        Long memberId = registerMember();
        String title = "조회 제목 테스트";
        String content = "조회 본문 테스트";
        Long postId = registerPost(memberId, title, content);

        try {
            postService.deletePost(memberId, postId);
            // 조회 및 검증
            assertFalse(postRepository.findById(postId).isPresent());
        } catch (RuntimeException e) {
            e.printStackTrace();
            fail();
        }
    }

    @DisplayName("회원 일치 여부 확인 테스트")
    @Test
    @Transactional
    void invalidMemberIdTest() {
        Long memberId = registerMember();
        String title = "조회 제목 테스트";
        String content = "조회 본문 테스트";
        Long postId = registerPost(memberId, title, content);

        try {
            postService.deletePost(0L, postId);
        } catch (RuntimeException e) {
            e.printStackTrace();
            assertTrue(true);
            return;
        }
        fail();
    }
}