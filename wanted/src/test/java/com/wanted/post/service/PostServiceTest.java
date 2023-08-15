package com.wanted.post.service;

import com.wanted.auth.entity.Member;
import com.wanted.auth.repository.MemberRepository;
import com.wanted.global.code.FailCode;
import com.wanted.global.code.RoleCode;
import com.wanted.global.exception.fail.InvalidArgsException;
import com.wanted.post.dto.response.FindAllPostResDto;
import com.wanted.post.dto.response.FindPostResDto;
import com.wanted.post.entity.Post;
import com.wanted.post.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

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
            e.printStackTrace();
            fail();
        }
    }

    @DisplayName("게시물 목록 조회 테스트")
    @Test
    @Transactional
    void findAllPostTest() {
        registerPostList(5);

        int page = 1;
        int size = 5;
        int num = 5;

        try {
            if (page <= 0) {
                throw new RuntimeException();
            }
            List<Post> postList = postRepository.findAllByOrderByPostIdDesc(PageRequest.of(page - 1, size)).toList();
            List<FindAllPostResDto> findAllPostResDtoList = FindAllPostResDto.toList(postList);

            // 검증
            for (int idx = 0; idx < 5; idx++) {
                assertEquals(String.valueOf(--num), findAllPostResDtoList.get(idx).getTitle());
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            fail();
        }
    }

    @DisplayName("게시물 조회 테스트")
    @Test
    @Transactional
    void findPostTest() {
        int num = 0;
        Long postId = registerPost(num);

        try {
            Post post = postRepository.findById(postId)
                    .orElseThrow(RuntimeException::new);

            FindPostResDto findPostResDto = FindPostResDto.of(post);

            assertEquals(String.valueOf(num), findPostResDto.getTitle());
        } catch (RuntimeException e) {
            e.printStackTrace();
            fail();
        }
    }
}