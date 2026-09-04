package com.hush0k.shareRoute.gamefication.internal.achievement.dto.request;

import com.hush0k.shareRoute.gamefication.internal.achievement.domain.Achievement;
import com.hush0k.shareRoute.gamefication.internal.achievement.domain.AchievementCondition;
import com.hush0k.shareRoute.gamefication.internal.achievement.enums.ConditionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;
import java.util.Map;

public record AchievementCreateDto(
        @NotNull Integer level,
        @NotNull Integer exp,
        String image,
        @NotNull @Valid AchievementTranslationCreateDto translation,
        @NotNull @Valid AchievementConditionCreateDto condition
) {}
