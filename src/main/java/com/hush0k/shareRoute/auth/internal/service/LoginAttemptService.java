package com.hush0k.shareRoute.auth.internal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class LoginAttemptService {

    private static final String ATTEMPTS_PREFIX = "auth:login_attempts:";
    private static final String BLOCK_PREFIX = "auth:login_block:";
    private static final String BLOCK_LEVEL_PREFIX = "auth:block_level:";
    private static final int MAX_ATTEMPTS = 3;
    private static final Duration ATTEMPTS_WINDOW = Duration.ofMinutes(10);

    private static final Duration[] BLOCK_DURATIONS = {
            Duration.ofMinutes(10),
            Duration.ofMinutes(30),
            Duration.ofHours(1),
            Duration.ofHours(8),
            Duration.ofDays(3),
            Duration.ofDays(7),
    };

    private static final Duration BLOCK_LEVEL_TTL = Duration.ofDays(7);

    private final StringRedisTemplate redisTemplate;

    public boolean isBlocked(String identifier) {
        String key = BLOCK_PREFIX + normalize(identifier);
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void registerFailure(String identifier) {
        String norm = normalize(identifier);
        String attemptKey = ATTEMPTS_PREFIX + norm;

        Long attempts = redisTemplate.opsForValue().increment(attemptKey);
        if (attempts != null && attempts == 1L) {
            redisTemplate.expire(attemptKey, ATTEMPTS_WINDOW);
        }

        if (attempts != null && attempts >= MAX_ATTEMPTS) {
            blockWithProgression(norm);
            redisTemplate.delete(attemptKey);
        }
    }

    public void registerSuccess(String identifier) {
        String norm = normalize(identifier);
        redisTemplate.delete(ATTEMPTS_PREFIX + norm);
        redisTemplate.delete(BLOCK_PREFIX + norm);
    }

    private void blockWithProgression(String norm) {
        String levelKey = BLOCK_LEVEL_PREFIX + norm;

        Long level = redisTemplate.opsForValue().increment(levelKey);
        if (level != null && level == 1L) {
            redisTemplate.expire(levelKey, BLOCK_LEVEL_TTL);
        }

        int index = (int) Math.min(level == null ? 1 : level, BLOCK_DURATIONS.length) - 1;
        Duration duration = BLOCK_DURATIONS[index];

        redisTemplate.opsForValue().set(BLOCK_PREFIX + norm, "1", duration);
    }

    private String normalize(String identifier) {
        return identifier.trim().toLowerCase();
    }

}
