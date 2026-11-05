package com.hush0k.shareRoute.gamefication.internal.achievement.exceptions;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

import java.util.UUID;

public class AchievementConditionNotFoundException extends MessageKeyException {
    public AchievementConditionNotFoundException(Long conditionId) {
        super("achievement.condition.notFound", conditionId);
    }

    public AchievementConditionNotFoundException(UUID achievementId) {
        super("achievement.condition.notFound", achievementId);
    }
}
