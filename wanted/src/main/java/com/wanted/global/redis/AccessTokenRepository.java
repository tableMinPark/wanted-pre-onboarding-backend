package com.wanted.global.redis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class AccessTokenRepository {
    private final RedisTemplate<String, AccessToken> accessTokenRedisTemplate;

    public AccessTokenRepository(@Qualifier("redisAccessTokenTemplate") RedisTemplate<String, AccessToken> accessTokenRedisTemplate) {
        this.accessTokenRedisTemplate = accessTokenRedisTemplate;
    }

    public void save(Long memberId, AccessToken accessToken) {
        String key = memberId.toString();
        ValueOperations<String, AccessToken> valueOperations = accessTokenRedisTemplate.opsForValue();
        valueOperations.set(key, accessToken);
        accessTokenRedisTemplate.expire(key, accessToken.getExpire(), TimeUnit.MILLISECONDS);
    }

    public Optional<AccessToken> findByMemberId(Long memberId) {
        String key = memberId.toString();
        ValueOperations<String, AccessToken> valueOperations = accessTokenRedisTemplate.opsForValue();
        return Optional.ofNullable(valueOperations.get(key));
    }

    public void deleteByMemberId(Long memberId) {
        String key = memberId.toString();
        accessTokenRedisTemplate.expire(key, 0, TimeUnit.MILLISECONDS);
    }
}
