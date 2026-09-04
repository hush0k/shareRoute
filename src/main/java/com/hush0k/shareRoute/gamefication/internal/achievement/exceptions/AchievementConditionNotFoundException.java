package com.hush0k.shareRoute.gamefication.internal.achievement.exceptions;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

public class AchievementConditionNotFoundException extends MessageKeyException {
    public AchievementConditionNotFoundException(Long conditionId) {
        super("achievement.condition.notFound", conditionId);
    }
}
