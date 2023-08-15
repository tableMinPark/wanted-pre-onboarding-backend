package com.wanted.post.service;

import com.wanted.auth.entity.Member;
import com.wanted.global.code.FailCode;
import com.wanted.global.exception.fail.InvalidArgsException;
import com.wanted.global.exception.fail.NotFoundException;
import com.wanted.global.exception.fail.UnAuthException;
import com.wanted.post.dto.response.FindAllPostResDto;
import com.wanted.post.dto.response.FindPostResDto;
import com.wanted.post.entity.Post;
import com.wanted.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public void registerPost(Long memberId, String title, String content) {
        if (!StringUtils.hasText(title)) {
            throw new InvalidArgsException(FailCode.INVALID_TITLE);
        }
        else if (!StringUtils.hasText(content)) {
            throw new InvalidArgsException(FailCode.INVALID_CONTENT);
        }

        Post post = Post.builder()
                .member(Member.builder()
                        .memberId(memberId)
                        .build())
                .title(title)
                .content(content)
                .build();
        postRepository.save(post);
    }

    @Transactional
    public List<FindAllPostResDto> findAllPost(Integer page, Integer size) {
        if (page <= 0) {
            throw new InvalidArgsException(FailCode.INVALID_PAGE);
        }
        List<Post> postList = postRepository.findAllByOrderByPostIdDesc(PageRequest.of(page - 1, size)).toList();
        return FindAllPostResDto.toList(postList);
    }

    @Transactional
    public FindPostResDto findPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(FailCode.NOT_FOUND_POST));
        return FindPostResDto.of(post);
    }

    @Transactional
    public void modifyPost(Long memberId, Long postId, String title, String content) {
        if (!StringUtils.hasText(title)) {
            throw new InvalidArgsException(FailCode.INVALID_TITLE);
        }
        else if (!StringUtils.hasText(content)) {
            throw new InvalidArgsException(FailCode.INVALID_CONTENT);
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(FailCode.NOT_FOUND_POST));

        // 게시물과 회원 일치 여부 확인
        if (!memberId.equals(post.getMember().getMemberId())) {
            throw new UnAuthException(FailCode.UN_AUTHENTICATION_POST);
        }

        post.setTitle(title);
        post.setContent(content);
    }

    @Transactional
    public void deletePost(Long memberId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(FailCode.NOT_FOUND_POST));

        if (!memberId.equals(post.getMember().getMemberId())) {
            throw new UnAuthException(FailCode.UN_AUTHENTICATION_POST);
        }

        postRepository.deleteById(postId);
    }
}
