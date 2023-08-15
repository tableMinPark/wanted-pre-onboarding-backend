package com.wanted.post.repository;

import com.wanted.auth.entity.Member;
import com.wanted.auth.repository.MemberRepository;
import com.wanted.global.code.RoleCode;
import com.wanted.post.entity.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostRepository postRepository;

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

    @DisplayName("게시물 저장 및 조회 테스트")
    @Test
    @Transactional
    void registerAndFindPostTest() {
        Long memberId = registerMember();
        String title = "제목 테스트";
        String content = "본문 테스트";
        // 저장
        Post registerPost = Post.builder()
                .member(Member.builder()
                        .memberId(memberId)
                        .build())
                .title(title)
                .content(content)
                .build();
        postRepository.save(registerPost);
        // 조회 및 검증
        Optional<Post> post = postRepository.findById(registerPost.getPostId());
        assertTrue(post.isPresent());
    }

    @DisplayName("Post 수정 테스트")
    @Test
    @Transactional
    void modifyPostTest() {
        Long memberId = registerMember();
        String title = "제목 테스트";
        String content = "본문 테스트";
        // 등록
        Post registerPost = Post.builder()
                .member(Member.builder()
                        .memberId(memberId)
                        .build())
                .title(title)
                .content(content)
                .build();
        postRepository.save(registerPost);
        // 조회
        Post post = postRepository.findById(registerPost.getPostId()).get();
        assertNotNull(post);
        // 수정
        String modifyTitle = "제목 수정 테스트";
        String modifyContent = "본문 수정 테스트";
        post.setTitle(modifyTitle);
        post.setContent(modifyContent);
        postRepository.flush();

        // 재 조회 및 검증 (수정한 게시물)
        post = postRepository.findById(registerPost.getPostId()).get();
        assertNotNull(post);
        assertNotEquals(title, post.getTitle());
        assertNotEquals(content, post.getContent());
    }

    @DisplayName("Post 삭제 테스트")
    @Test
    @Transactional
    void deletePostTest() {
        Long memberId = registerMember();
        String title = "제목 테스트";
        String content = "본문 테스트";
        // 등록
        Post registerPost = Post.builder()
                .member(Member.builder()
                        .memberId(memberId)
                        .build())
                .title(title)
                .content(content)
                .build();
        postRepository.save(registerPost);

        Long postId = registerPost.getPostId();
        postRepository.deleteById(postId);

        Optional<Post> post = postRepository.findById(postId);
        assertFalse(post.isPresent());
    }
}