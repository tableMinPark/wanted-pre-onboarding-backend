package com.wanted.post.controller;

import com.wanted.auth.entity.Member;
import com.wanted.auth.repository.MemberRepository;
import com.wanted.global.code.RoleCode;
import com.wanted.post.dto.response.FindAllPostResDto;
import com.wanted.post.dto.response.FindPostResDto;
import com.wanted.post.entity.Post;
import com.wanted.post.repository.PostRepository;
import com.wanted.post.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FindPostTest {
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

    Long registerPost(int num) {
        Long memberId = registerMember();

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

        return post.getPostId();
    }

    @DisplayName("게시물 조회 테스트")
    @Test
    @Transactional
    void findPostTest() {
        Long postId = registerPost(0);

        try {
            FindPostResDto findPostResDto = postService.findPost(postId);
            assertEquals(postId, findPostResDto.getPostId());
        } catch (RuntimeException e) {
            e.printStackTrace();
            fail();
        }
    }
}