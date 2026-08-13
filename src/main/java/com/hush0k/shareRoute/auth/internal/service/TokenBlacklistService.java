package com.hush0k.shareRoute.auth.internal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.HexFormat;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private static final String BLACKLIST_PREFIX = "auth:blacklist:";

    private final JwtService jwtService;
    private final StringRedisTemplate redisTemplate;

    public void blacklist(String accessToken){
        Instant expiration = jwtService.extractExpiration(accessToken);
        long ttlBetween = Duration.between(Instant.now(), expiration).toSeconds();

        if (ttlBetween > 0) {
            String tokenHash = hashToken(accessToken);
            redisTemplate.opsForValue().set(
                    BLACKLIST_PREFIX + tokenHash,
                    "1",
                    Duration.ofSeconds(ttlBetween)
            );
        }
    }

    public boolean isBlacklisted(String accessToken){
        String tokenHash = BLACKLIST_PREFIX + hashToken(accessToken);
        return Boolean.TRUE.equals(redisTemplate.hasKey(tokenHash));
    }

    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }
    }

}
