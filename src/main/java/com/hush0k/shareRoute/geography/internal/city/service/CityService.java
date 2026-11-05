package com.hush0k.shareRoute.geography.internal.city.service;

import com.hush0k.shareRoute.geography.internal.city.dto.response.CityResponseDto;
import com.hush0k.shareRoute.geography.internal.city.exceptions.CityNotFoundException;
import com.hush0k.shareRoute.geography.internal.city.mapper.CityMapper;
import com.hush0k.shareRoute.geography.internal.city.repository.CityRepository;
import com.hush0k.shareRoute.common.global_dto.PageResponse;
import com.hush0k.shareRoute.geography.internal.city.domain.City;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Transactional(readOnly = true)
    public CityResponseDto getCityById(Integer id) {
        return cityMapper.cityToCityDto(cityRepository.findById(id).orElseThrow(
                () -> {
                    log.warn("No city found with id {}", id);
                    return new CityNotFoundException(id);
                }
        ));
    }

    @Transactional(readOnly = true)
    public PageResponse<CityResponseDto> getCities(Integer countryId, Pageable pageable) {
        Page<City> cities = countryId == null
                ? cityRepository.findAll(pageable)
                : cityRepository.findAllByCountryId(countryId, pageable);
        return PageResponse.of(cities.map(cityMapper::cityToCityDto));
    }
}
