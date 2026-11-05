package com.hush0k.shareRoute.gamefication.internal.achievement.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AchievementFullResponseDto(
        UUID id,
        String name,
        String description,
        Integer level,
        Integer exp,
        String image,
        AchievementConditionBriefDto condition
) {}