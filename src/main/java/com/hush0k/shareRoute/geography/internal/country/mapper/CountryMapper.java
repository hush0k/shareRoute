package com.hush0k.shareRoute.geography.internal.country.mapper;

import com.hush0k.shareRoute.geography.internal.country.domain.Country;
import com.hush0k.shareRoute.geography.internal.country.dto.response.CountryResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    CountryResponseDto countryToCountryDto(Country country);
}
