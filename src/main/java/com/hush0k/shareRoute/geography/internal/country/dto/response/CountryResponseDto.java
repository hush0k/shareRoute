package com.hush0k.shareRoute.geography.internal.country.dto.response;

public record CountryResponseDto(
        Integer id,
        String isoCode,
        String phoneCode,
        String currency
) {}
