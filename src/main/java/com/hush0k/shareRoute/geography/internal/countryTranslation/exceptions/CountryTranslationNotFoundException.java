package com.hush0k.shareRoute.geography.internal.countryTranslation.exceptions;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

public class CountryTranslationNotFoundException extends MessageKeyException {
    public CountryTranslationNotFoundException(Integer countryId, String languageCode) {
        super("country.translation.notFoundByCountryAndLanguage", countryId, languageCode);
    }
}
