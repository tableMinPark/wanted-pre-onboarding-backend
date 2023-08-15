package com.wanted.post.controller;

import com.wanted.auth.entity.Member;
import com.wanted.auth.repository.MemberRepository;
import com.wanted.global.code.RoleCode;
import com.wanted.post.dto.response.FindPostResDto;
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
class ModifyPostTest {
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

        return post.getPostId();
    }

    @DisplayName("게시물 수정 테스트")
    @Test
    @Transactional
    void modifyPostTest() {
        Long memberId = registerMember();
        String title = "조회 제목 테스트";
        String content = "조회 본문 테스트";
        Long postId = registerPost(memberId, title, content);

        String modifyTitle = "수정 제목 테스트";
        String modifyContent = "수정 본문 테스트";

        try {
            postService.modifyPost(memberId, postId, modifyTitle, modifyContent);
            // 조회 및 검증
            Post post = postRepository.findById(postId)
                                    .orElseThrow(RuntimeException::new);
            assertNotEquals(title, post.getTitle());
            assertNotEquals(content, post.getContent());
        } catch (RuntimeException e) {
            e.printStackTrace();
            fail();
        }
    }

    @DisplayName("제목 유효성 테스트")
    @Test
    @Transactional
    void invalidArgsTest() {
        Long memberId = registerMember();
        String title = "조회 제목 테스트";
        String content = "조회 본문 테스트";
        Long postId = registerPost(memberId, title, content);

        String modifyTitle = "";
        String modifyContent = "수정 본문 테스트";

        try {
            postService.modifyPost(memberId, postId, modifyTitle, modifyContent);
        } catch (RuntimeException e) {
            e.printStackTrace();
            assertTrue(true);
            return;
        }
        fail();
    }

    @DisplayName("제목 유효성 테스트")
    @Test
    @Transactional
    void invalidArgsTest2() {
        Long memberId = registerMember();
        String title = "조회 제목 테스트";
        String content = "조회 본문 테스트";
        Long postId = registerPost(memberId, title, content);

        String modifyTitle = "수정 제목 테스트";
        String modifyContent = "";

        try {
            postService.modifyPost(memberId, postId, modifyTitle, modifyContent);
        } catch (RuntimeException e) {
            e.printStackTrace();
            assertTrue(true);
            return;
        }
        fail();
    }
}