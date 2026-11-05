package com.hush0k.shareRoute.geography.internal.countryTranslation.mapper;

import com.hush0k.shareRoute.geography.internal.countryTranslation.domain.CountryTranslation;
import com.hush0k.shareRoute.geography.internal.countryTranslation.dto.response.CountryTranslationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CountryTranslationMapper {
    @Mapping(target = "countryId", source = "country.id")
    @Mapping(target = "isoCode", source = "country.isoCode")
    @Mapping(target = "phoneCode", source = "country.phoneCode")
    @Mapping(target = "currency", source = "country.currency")
    CountryTranslationResponseDto countryToDto(CountryTranslation countryTranslation);
}
