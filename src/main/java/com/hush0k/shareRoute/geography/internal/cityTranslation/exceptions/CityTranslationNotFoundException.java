package com.hush0k.shareRoute.geography.internal.cityTranslation.exceptions;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

public class CityTranslationNotFoundException extends MessageKeyException {
    public CityTranslationNotFoundException(Integer cityId, String languageCode) {
        super("city.translation.notFoundByCityAndLanguage", cityId, languageCode);
    }
}
