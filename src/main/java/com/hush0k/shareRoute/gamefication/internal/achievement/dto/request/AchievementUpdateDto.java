package com.hush0k.shareRoute.gamefication.internal.achievement.dto.request;

import java.util.UUID;

public record AchievementUpdateDto(
        Integer level,
        Integer exp,
        String image,
        AchievementConditionUpdateDto condition,
        AchievementTranslationUpdateDto translation,
        Long conditionId,
        UUID translationId
) {}
