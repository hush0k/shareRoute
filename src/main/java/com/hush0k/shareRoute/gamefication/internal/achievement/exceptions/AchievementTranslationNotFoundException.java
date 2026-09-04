package com.hush0k.shareRoute.gamefication.internal.achievement.exceptions;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

import java.util.UUID;

public class AchievementTranslationNotFoundException extends MessageKeyException {
    public AchievementTranslationNotFoundException(UUID translationId) {
        super("achievement.translation.notFound", translationId);
    }
}
