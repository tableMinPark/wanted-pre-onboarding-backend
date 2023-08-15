package com.wanted.post.service;

import com.wanted.auth.entity.Member;
import com.wanted.auth.repository.MemberRepository;
import com.wanted.global.code.FailCode;
import com.wanted.global.code.RoleCode;
import com.wanted.global.exception.fail.InvalidArgsException;
import com.wanted.global.exception.fail.UnAuthException;
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

    Long registerPost(String title, String content) {
        Long memberId = registerMember();

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
        String title = "조회 제목 테스트";
        String content = "조회 본문 테스트";
        Long postId = registerPost(title, content);

        try {
            Post post = postRepository.findById(postId)
                    .orElseThrow(RuntimeException::new);

            FindPostResDto findPostResDto = FindPostResDto.of(post);

            assertEquals(title, findPostResDto.getTitle());
            assertEquals(content, findPostResDto.getContent());
        } catch (RuntimeException e) {
            e.printStackTrace();
            fail();
        }
    }

    @DisplayName("게시물 수정 테스트")
    @Test
    @Transactional
    void modifyPostTest() {
        Long memberId = registerMember();
        String title = "조회 제목 테스트";
        String content = "조회 본문 테스트";
        Long postId = registerPost(memberId, title, content);

        try {
            Post post = postRepository.findById(postId)
                    .orElseThrow(RuntimeException::new);

            // 게시물과 회원 일치 여부 확인
            if (!memberId.equals(post.getMember().getMemberId())) {
                throw new UnAuthException(FailCode.UN_AUTHENTICATION_POST);
            }

            String modifyTitle = "수정 제목 테스트";
            String modifyContent = "수정 본문 테스트";
            post.setTitle(modifyTitle);
            post.setContent(modifyContent);
            postRepository.flush();

            // 재 조회 및 검증
            post = postRepository.findById(postId)
                    .orElseThrow(RuntimeException::new);

            assertNotEquals(title, post.getTitle());
            assertNotEquals(content, post.getContent());
        } catch (RuntimeException e) {
            e.printStackTrace();
            fail();
        }
    }
}