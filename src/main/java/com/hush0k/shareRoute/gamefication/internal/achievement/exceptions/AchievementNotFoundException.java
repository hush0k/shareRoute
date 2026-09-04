package com.hush0k.shareRoute.gamefication.internal.achievement.exceptions;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

import java.util.UUID;

public class AchievementNotFoundException extends MessageKeyException {
    public AchievementNotFoundException(UUID achievementId) {
        super("achievement.notFound", achievementId);
    }
}
