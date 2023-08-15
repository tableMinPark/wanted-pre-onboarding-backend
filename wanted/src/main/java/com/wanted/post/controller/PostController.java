package com.wanted.post.controller;

import com.wanted.auth.dto.request.LoginMemberReqDto;
import com.wanted.auth.dto.request.RegisterMemberReqDto;
import com.wanted.auth.dto.response.LoginMemberResDto;
import com.wanted.auth.service.AuthService;
import com.wanted.global.code.FailCode;
import com.wanted.global.exception.fail.NotFoundException;
import com.wanted.global.response.SuccessResponse;
import com.wanted.global.security.AuthoritiesUtil;
import com.wanted.post.dto.request.ModifyPostReqDto;
import com.wanted.post.dto.request.RegisterPostReqDto;
import com.wanted.post.dto.response.FindAllPostResDto;
import com.wanted.post.dto.response.FindPostResDto;
import com.wanted.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PostMapping("")
    private ResponseEntity<Object> registerPost(RegisterPostReqDto registerPostReqDto) {
        log.info("registerPost - Call");

        Long memberId = AuthoritiesUtil.getMemberId();
        String title = registerPostReqDto.getTitle();
        String content = registerPostReqDto.getContent();

        if (title == null || content == null) {
            throw new NotFoundException(FailCode.INVALID_ARGS);
        }

        postService.registerPost(memberId, title, content);
        return ResponseEntity.ok().body(new SuccessResponse(null));
    }

    @GetMapping("")
    private ResponseEntity<Object> findAllPost(@RequestParam("page") Integer page,
                                               @RequestParam("size") Integer size) {
        log.info("findAllPost - Call");

        if (page == null || size == null) {
            throw new NotFoundException(FailCode.INVALID_ARGS);
        }

        List<FindAllPostResDto> findAllPostResDtoList = postService.findAllPost(page, size);
        return ResponseEntity.ok().body(new SuccessResponse(findAllPostResDtoList));
    }

    @GetMapping("/{postId}")
    private ResponseEntity<Object> findPost(@PathVariable("postId") Integer postId) {
        log.info("findPost - Call");

        if (postId == null) {
            throw new NotFoundException(FailCode.INVALID_ARGS);
        }

        FindPostResDto findPostResDto = postService.findPost(postId);
        return ResponseEntity.ok().body(new SuccessResponse(findPostResDto));
    }

    @PutMapping("/{postId}")
    private ResponseEntity<Object> modifyPost(@PathVariable("postId") Integer postId,
                                              ModifyPostReqDto modifyPostReqDto) {
        log.info("modifyPost - Call");

        Long memberId = AuthoritiesUtil.getMemberId();
        String title = modifyPostReqDto.getTitle();
        String content = modifyPostReqDto.getContent();

        if (postId == null || title == null || content == null) {
            throw new NotFoundException(FailCode.INVALID_ARGS);
        }

        postService.modifyPost(memberId, postId, title, content);
        return ResponseEntity.ok().body(new SuccessResponse(null));
    }

    @DeleteMapping("/{postId}")
    private ResponseEntity<Object> deletePost(@PathVariable("postId") Integer postId) {
        log.info("deletePost - Call");

        Long memberId = AuthoritiesUtil.getMemberId();

        if (postId == null) {
            throw new NotFoundException(FailCode.INVALID_ARGS);
        }

        postService.deletePost(memberId, postId);
        return ResponseEntity.ok().body(new SuccessResponse(null));
    }
}
