package com.hush0k.shareRoute.geography.internal.city.dto.response;

import com.hush0k.shareRoute.geography.internal.city.enums.CityType;

public record CityResponseDto(
        Integer id,
        Integer countryId,
        String originalName,
        CityType cityType,
        Double latitude,
        Double longitude,
        String timezone
) {}
