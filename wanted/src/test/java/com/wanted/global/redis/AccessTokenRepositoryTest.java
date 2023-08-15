package com.wanted.global.redis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Redis 테스트")
@SpringBootTest
class AccessTokenRepositoryTest {
    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @DisplayName("AccessToken 저장 및 조회 테스트")
    @Test
    void registerAndFindAccessTokenTest() {
        Long memberId = 0L;
        String accessToken = UUID.randomUUID().toString();
        long expired = 1000L * 15L;

        AccessToken token = AccessToken.builder()
                .accessToken(accessToken)
                .memberId(memberId)
                .expire(expired)
                .expireTime(LocalDateTime.now().plusSeconds(expired / 1000))
                .build();
        // 저장
        accessTokenRepository.save(memberId, token);
        // 조회
        token = accessTokenRepository.findByMemberId(memberId).get();
        // 삭제
        accessTokenRepository.deleteByMemberId(memberId);

        assertNotNull(token);
        assertEquals(accessToken, token.getAccessToken());
    }
}