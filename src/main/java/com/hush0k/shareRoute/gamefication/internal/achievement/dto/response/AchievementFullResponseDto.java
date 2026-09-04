package com.hush0k.shareRoute.gamefication.internal.achievement.dto.response;

import com.hush0k.shareRoute.gamefication.internal.achievement.domain.AchievementCondition;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record AchievementFullResponseDto(
        UUID id,
        String name,
        String description,
        Integer level,
        Integer exp,
        String image,
        List<AchievementConditionBriefDto> conditions
) {}