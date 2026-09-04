package com.hush0k.shareRoute.gamefication.internal.achievement.dto.response;

import com.hush0k.shareRoute.gamefication.internal.achievement.domain.Achievement;
import com.hush0k.shareRoute.gamefication.internal.achievement.enums.ConditionType;

import java.util.Map;
import java.util.UUID;

public record AchievementConditionResponseDto(
        Long id,
        ConditionType conditionType,
        Long numberOfAction,
        Achievement achievement,
        Map<String, Object> metadata
) {}
