package com.hush0k.shareRoute.geography.internal.country.exceptions;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

public class CountryNotFoundException extends MessageKeyException {
    public CountryNotFoundException(Integer countryId) {
        super("country.notFound", countryId);
    }
}
