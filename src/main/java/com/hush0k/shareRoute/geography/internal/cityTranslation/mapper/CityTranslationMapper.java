package com.hush0k.shareRoute.geography.internal.cityTranslation.mapper;

import com.hush0k.shareRoute.geography.internal.cityTranslation.domain.CityTranslation;
import com.hush0k.shareRoute.geography.internal.cityTranslation.dto.response.CityTranslationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CityTranslationMapper {
    @Mapping(target = "cityId", source = "city.id")
    @Mapping(target = "countryId", source = "city.country.id")
    @Mapping(target = "originalName", source = "city.originalName")
    @Mapping(target = "latitude", source = "city.latitude")
    @Mapping(target = "longitude", source = "city.longitude")
    @Mapping(target = "timezone", source = "city.timezone")
    CityTranslationResponseDto cityToDto(CityTranslation cityTranslation);
}
