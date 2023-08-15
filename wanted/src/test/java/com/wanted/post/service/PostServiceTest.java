package com.wanted.post.service;

import com.wanted.auth.entity.Member;
import com.wanted.auth.repository.MemberRepository;
import com.wanted.global.code.RoleCode;
import com.wanted.post.entity.Post;
import com.wanted.post.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {
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

    @DisplayName("게시물 등록 테스트")
    @Test
    @Transactional
    void registerPostTest() {
        Long memberId = registerMember();
        String title = "제목 테스트";
        String content = "본문 테스트";

        try {
            if (!StringUtils.hasText(title) || !StringUtils.hasText(content)) {
                throw new RuntimeException();
            }

            Post post = Post.builder()
                    .member(Member.builder()
                            .memberId(memberId)
                            .build())
                    .title(title)
                    .content(content)
                    .build();
            postRepository.save(post);

            // 저장 확인
            postRepository.findById(post.getPostId())
                    .orElseThrow(RuntimeException::new);
        } catch (RuntimeException e) {
            fail();
        }
    }
}