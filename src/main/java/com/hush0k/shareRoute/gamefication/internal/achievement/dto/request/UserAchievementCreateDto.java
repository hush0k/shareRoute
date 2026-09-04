package com.hush0k.shareRoute.gamefication.internal.achievement.dto.request;

import java.util.UUID;

public record UserAchievementCreateDto(
        UUID userId,
        UUID achievementId
) {}
