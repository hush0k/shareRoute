package com.hush0k.shareRoute.geography.internal.countryTranslation.dto.response;

public record CountryTranslationResponseDto(
        Integer id,
        Integer countryId,
        String name,
        String isoCode,
        String phoneCode,
        String currency,
        String languageCode
) {}
