package com.hush0k.shareRoute.gamefication.internal.archetype.service;

import com.hush0k.shareRoute.gamefication.internal.archetype.domain.ArchetypeTranslationModel;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.TranslationCreateDto;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.TranslationUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.response.PageResponse;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.response.TranslationResponseDto;
import com.hush0k.shareRoute.gamefication.internal.archetype.exceptions.TranslationNotFoundException;
import com.hush0k.shareRoute.gamefication.internal.archetype.mapper.TranslationMapper;
import com.hush0k.shareRoute.gamefication.internal.archetype.repository.ArchetypeTranslationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ArchetypeTranslationService {

    private final ArchetypeTranslationRepository translationRepository;
    private final TranslationMapper  translationMapper;

    public void createTranslation(TranslationCreateDto dto) {
        ArchetypeTranslationModel newTranslation = translationRepository.save(translationMapper.toArchetypeTranslationModel(dto));
    }

    public TranslationResponseDto updateTranslation(TranslationUpdateDto dto, Long translationId) {
        ArchetypeTranslationModel archetypeTranslationModel = getArchetypeTranslationModel(translationId);
        translationMapper.updateTranslation(dto, archetypeTranslationModel);

        ArchetypeTranslationModel updatedTranslation = translationRepository.save(archetypeTranslationModel);
        return translationMapper.toTranslationResponseDto(updatedTranslation);
    }

    public void deleteTranslation(Long translationId) {
        ArchetypeTranslationModel archetypeTranslationModel = getArchetypeTranslationModel(translationId);
        translationRepository.delete(archetypeTranslationModel);
    }

    @Transactional(readOnly = true)
    public TranslationResponseDto getTranslationById(Long translationId) {
        ArchetypeTranslationModel archetypeTranslationModel = getArchetypeTranslationModel(translationId);
        return translationMapper.toTranslationResponseDto(archetypeTranslationModel);
    }

    @Transactional(readOnly = true)
    public PageResponse<TranslationResponseDto> getArchetypeTranslationsByLanguage(Pageable pageable, String language) {
        Page<ArchetypeTranslationModel> translationModels = translationRepository.findAllByLang(language, pageable);
        Page<TranslationResponseDto> mapped = translationModels.map(translationMapper::toTranslationResponseDto);
        return PageResponse.of(mapped);
    }

    @Transactional(readOnly = true)
    public PageResponse<TranslationResponseDto> getArchetypeTranslationsByArchetypeId(Long archetypeId, Pageable pageable) {
        Page<ArchetypeTranslationModel> translationModels = translationRepository.findAllByArchId(archetypeId, pageable);
        Page<TranslationResponseDto> mapped = translationModels.map(translationMapper::toTranslationResponseDto);
        return PageResponse.of(mapped);
    }

    @Transactional(readOnly = true)
    public List<ArchetypeTranslationModel> getArchetypeTranslationModelsByLanguage(List<Long> archetypeIds,
                                                                                   String language) {
        return translationRepository.findAllByArchetypeIdInAndLanguageCode(archetypeIds, language);
    }

    private ArchetypeTranslationModel getArchetypeTranslationModel(Long translationId) {
        return translationRepository.findById(translationId)
                .orElseThrow(
                        () -> {
                            log.warn("Could not find translation with id {}", translationId);
                            return new TranslationNotFoundException(translationId);
                        }
                );
    }
}
