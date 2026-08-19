package com.hush0k.shareRoute.gamefication.internal.archetype.exceptions;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

public class TranslationNotFoundException extends MessageKeyException {
    public TranslationNotFoundException(Long translationId) {
        super("archetype.translation.notFound", translationId);
    }

    public TranslationNotFoundException(Long archetypeId, String languageCode) {
        super("archetype.translation.notFoundByArchetypeAndLanguage", archetypeId, languageCode);
    }
}
