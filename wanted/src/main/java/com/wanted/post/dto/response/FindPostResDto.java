package com.wanted.post.dto.response;

import com.wanted.post.entity.Post;
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

    public static FindPostResDto of(Post post) {
        Long postId = post.getPostId();
        Long memberId = post.getMember().getMemberId();
        String title = post.getTitle();
        String content = post.getContent();
        LocalDateTime regDt = post.getRegDt();

        return FindPostResDto.builder()
                .postId(postId)
                .memberId(memberId)
                .title(title)
                .content(content)
                .regDt(regDt)
                .build();
    }
}
