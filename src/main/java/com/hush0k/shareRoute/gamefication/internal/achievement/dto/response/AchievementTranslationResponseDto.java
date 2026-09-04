package com.hush0k.shareRoute.gamefication.internal.achievement.dto.response;

import com.hush0k.shareRoute.gamefication.internal.achievement.domain.Achievement;

import java.util.UUID;

public record AchievementTranslationResponseDto(
        UUID id,
        String languageCode,
        Achievement achievement,
        String name,
        String description
) {}
