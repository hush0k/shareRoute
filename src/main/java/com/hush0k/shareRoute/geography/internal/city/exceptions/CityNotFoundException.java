package com.hush0k.shareRoute.geography.internal.city.exceptions;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

public class CityNotFoundException extends MessageKeyException {
    public CityNotFoundException(Integer cityId) {
        super("city.notFound", cityId);
    }
}
