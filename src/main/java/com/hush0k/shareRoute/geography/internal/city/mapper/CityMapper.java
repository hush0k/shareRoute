package com.hush0k.shareRoute.geography.internal.city.mapper;

import com.hush0k.shareRoute.geography.internal.city.domain.City;
import com.hush0k.shareRoute.geography.internal.city.dto.response.CityResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CityMapper {
    @Mapping(target = "countryId", source = "country.id")
    CityResponseDto cityToCityDto(City city);
}
