package com.hush0k.shareRoute.geography.internal.country.service;

import com.hush0k.shareRoute.geography.internal.country.dto.response.CountryResponseDto;
import com.hush0k.shareRoute.geography.internal.country.exceptions.CountryNotFoundException;
import com.hush0k.shareRoute.geography.internal.country.mapper.CountryMapper;
import com.hush0k.shareRoute.geography.internal.country.repository.CountryRepository;
import com.hush0k.shareRoute.common.global_dto.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Transactional(readOnly = true)
    public CountryResponseDto getCountryById(Integer id) {
        return countryMapper.countryToCountryDto(countryRepository.findById(id).orElseThrow(
                () -> {
                    log.warn("No country found with id {}", id);
                    return new CountryNotFoundException(id);
                }
        ));
    }

    @Transactional(readOnly = true)
    public PageResponse<CountryResponseDto> getCountries(Pageable pageable) {
        return PageResponse.of(countryRepository.findAll(pageable).map(countryMapper::countryToCountryDto));
    }
}
