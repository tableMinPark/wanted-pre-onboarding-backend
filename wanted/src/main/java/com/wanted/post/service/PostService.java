package com.wanted.post.service;

import com.wanted.post.dto.response.FindAllPostResDto;
import com.wanted.post.dto.response.FindPostResDto;
import com.wanted.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public void registerPost(String title, String content) {

    }

    public FindAllPostResDto findAllPost(Integer page, Integer size) {
        return FindAllPostResDto.builder().build();
    }

    public FindPostResDto findPost(Integer postId) {
        return FindPostResDto.builder().build();
    }

    public void modifyPost(Integer postId, String title, String content) {

    }

    public void deletePost(Integer postId) {

    }
}
