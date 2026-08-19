package com.hush0k.shareRoute.gamefication.internal.exceptions;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

public class TranslationNotFoundException extends MessageKeyException {
    public TranslationNotFoundException(Long translationId) {
        super("archetype.translation.notFound", translationId);
    }
}
