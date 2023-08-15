package com.wanted.post.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FindPostResDto {
    private Long postId;
    private Long memberId;
    private String title;
    private String content;
    private LocalDateTime regDt;
}
