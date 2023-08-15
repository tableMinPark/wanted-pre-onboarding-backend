package com.wanted.post.service;

import com.wanted.auth.entity.Member;
import com.wanted.global.code.FailCode;
import com.wanted.global.exception.fail.InvalidArgsException;
import com.wanted.post.dto.response.FindAllPostResDto;
import com.wanted.post.dto.response.FindPostResDto;
import com.wanted.post.entity.Post;
import com.wanted.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

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

    public FindAllPostResDto findAllPost(Integer page, Integer size) {
        return FindAllPostResDto.builder().build();
    }

    public FindPostResDto findPost(Integer postId) {
        return FindPostResDto.builder().build();
    }

    public void modifyPost(Long memberId, Integer postId, String title, String content) {

    }

    public void deletePost(Long memberId, Integer postId) {

    }
}
