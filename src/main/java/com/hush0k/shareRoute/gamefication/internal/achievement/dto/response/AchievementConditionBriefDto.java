package com.hush0k.shareRoute.gamefication.internal.achievement.dto.response;

import com.hush0k.shareRoute.gamefication.internal.achievement.domain.Achievement;
import com.hush0k.shareRoute.gamefication.internal.achievement.enums.ConditionType;

import java.util.Map;

public record AchievementConditionBriefDto(
        ConditionType conditionType,
        Long numberOfAction,
        Map<String, Object> metadata
) {}
