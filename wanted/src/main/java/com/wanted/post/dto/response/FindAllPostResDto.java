package com.wanted.post.dto.response;

import com.wanted.post.entity.Post;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class FindAllPostResDto {
    private Long memberId;
    private String title;
    private LocalDateTime regDt;

    private static FindAllPostResDto of(Post post) {
        Long memberId = post.getMember().getMemberId();
        String title = post.getTitle();
        LocalDateTime regDt = post.getRegDt();

        return FindAllPostResDto.builder()
                .memberId(memberId)
                .title(title)
                .regDt(regDt)
                .build();
    }

    public static List<FindAllPostResDto> toList(List<Post> postList) {
        return postList.stream()
                .map(FindAllPostResDto::of)
                .collect(Collectors.toList());
    }
}
