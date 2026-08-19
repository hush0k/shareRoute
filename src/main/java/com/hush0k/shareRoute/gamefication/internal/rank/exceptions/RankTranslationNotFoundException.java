package com.hush0k.shareRoute.gamefication.internal.rank.exceptions;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

public class RankTranslationNotFoundException extends MessageKeyException {
    public RankTranslationNotFoundException(Long rankId, String languageCode) {
        super("rank.translation.notFoundByRankAndLanguage", rankId, languageCode);
    }
}
