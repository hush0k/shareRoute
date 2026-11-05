package com.hush0k.shareRoute.geography.internal.countryTranslation.service;

import com.hush0k.shareRoute.geography.internal.countryTranslation.dto.response.CountryTranslationResponseDto;
import com.hush0k.shareRoute.geography.internal.countryTranslation.exceptions.CountryTranslationNotFoundException;
import com.hush0k.shareRoute.geography.internal.countryTranslation.mapper.CountryTranslationMapper;
import com.hush0k.shareRoute.geography.internal.countryTranslation.repository.CountryTranslationRepository;
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
public class CountryTranslationService {

    private final CountryTranslationRepository repository;
    private final CountryTranslationMapper mapper;

    @Transactional(readOnly = true)
    public CountryTranslationResponseDto findByIdCountryIdAndCountryCode(Integer countryId, String languageCode) {
        return repository.findByCountryIdAndLanguageCode(countryId, languageCode)
                .map(mapper::countryToDto)
                .orElseThrow(() -> {
                    log.warn("Country id {} not found for language code {}", countryId, languageCode);
                    return new CountryTranslationNotFoundException(countryId, languageCode);
                });
    }

    @Transactional(readOnly = true)
    public PageResponse<CountryTranslationResponseDto> getCountryTranslations(String languageCode, Pageable pageable) {
        return PageResponse.of(repository.findAllByLang(languageCode, pageable).map(mapper::countryToDto));
    }
}
