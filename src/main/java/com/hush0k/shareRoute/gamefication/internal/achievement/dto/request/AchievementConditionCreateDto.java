package com.hush0k.shareRoute.gamefication.internal.achievement.dto.request;

import com.hush0k.shareRoute.gamefication.internal.achievement.domain.Achievement;
import com.hush0k.shareRoute.gamefication.internal.achievement.enums.ConditionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.util.Map;

@Builder
public record AchievementConditionCreateDto(
        Achievement achievement,
        @NotNull ConditionType conditionType,
        @NotNull @Positive Long numberOfAction,
        Map<String, Object> metadata
) {}
