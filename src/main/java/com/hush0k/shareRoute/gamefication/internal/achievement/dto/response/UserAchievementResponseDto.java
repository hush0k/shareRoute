package com.hush0k.shareRoute.gamefication.internal.achievement.dto.response;

import java.time.Instant;
import java.util.UUID;

public record UserAchievementResponseDto(
        UUID id,
        UUID userId,
        AchievementFullResponseDto achievement,
        Long completedCount,
        Boolean isAchieved,
        Instant achievedAt
) {}
