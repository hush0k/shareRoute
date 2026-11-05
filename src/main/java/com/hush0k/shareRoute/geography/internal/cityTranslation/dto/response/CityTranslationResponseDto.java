package com.hush0k.shareRoute.geography.internal.cityTranslation.dto.response;

public record CityTranslationResponseDto(
        Integer id,
        Integer cityId,
        Integer countryId,
        String name,
        String originalName,
        Double latitude,
        Double longitude,
        String timezone,
        String languageCode
) {}
