package com.hush0k.shareRoute.gamefication.internal.achievement.dto.request;

import com.hush0k.shareRoute.gamefication.internal.achievement.enums.ConditionType;

import java.util.Map;

public record AchievementConditionUpdateDto(
        Long id,
        ConditionType conditionType,
        Long numberOfAction,
        Map<String, Object> metadata
) {}
