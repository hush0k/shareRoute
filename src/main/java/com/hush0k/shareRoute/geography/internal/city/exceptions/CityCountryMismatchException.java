package com.hush0k.shareRoute.geography.internal.city.exceptions;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

public class CityCountryMismatchException extends MessageKeyException {
    public CityCountryMismatchException(Integer cityId, Integer countryId) {
        super("city.countryMismatch", cityId, countryId);
    }
}
