package com.hush0k.shareRoute.geography.internal.cityTranslation.service;

import com.hush0k.shareRoute.geography.internal.cityTranslation.dto.response.CityTranslationResponseDto;
import com.hush0k.shareRoute.geography.internal.cityTranslation.exceptions.CityTranslationNotFoundException;
import com.hush0k.shareRoute.geography.internal.cityTranslation.mapper.CityTranslationMapper;
import com.hush0k.shareRoute.geography.internal.cityTranslation.repository.CityTranslationRepository;
import com.hush0k.shareRoute.common.global_dto.PageResponse;
import com.hush0k.shareRoute.geography.internal.cityTranslation.domain.CityTranslation;
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
public class CityTranslationService {

    private final CityTranslationRepository repository;
    private final CityTranslationMapper mapper;

    @Transactional(readOnly = true)
    public CityTranslationResponseDto findByCityIdAndLanguageCode(Integer cityId, String languageCode) {
        return repository.findByCityIdAndLanguageCode(cityId, languageCode)
                .map(mapper::cityToDto)
                .orElseThrow(() -> {
                    log.warn("City id {} not found for language code {}", cityId, languageCode);
                    return new CityTranslationNotFoundException(cityId, languageCode);
                });
    }

    @Transactional(readOnly = true)
    public PageResponse<CityTranslationResponseDto> getCityTranslations(String languageCode, Integer countryId, Pageable pageable) {
        Page<CityTranslation> translations = countryId == null
                ? repository.findAllByLang(languageCode, pageable)
                : repository.findAllByLangAndCountryId(languageCode, countryId, pageable);
        return PageResponse.of(translations.map(mapper::cityToDto));
    }
}
