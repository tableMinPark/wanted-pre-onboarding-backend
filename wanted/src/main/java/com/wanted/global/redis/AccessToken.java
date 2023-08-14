package com.wanted.global.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class AccessToken {
    @Id
    private Long memberId;
    private String accessToken;
    private LocalDateTime expireTime;
}
