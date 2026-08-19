package com.hush0k.shareRoute.gamefication.internal.archetype.service;

import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.ArchetypeModelUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.TranslationCreateDto;
import com.hush0k.shareRoute.common.global_dto.PageResponse;
import com.hush0k.shareRoute.gamefication.internal.archetype.enums.ArchetypeState;
import com.hush0k.shareRoute.gamefication.internal.archetype.exceptions.ArchetypeModelNotFoundException;
import com.hush0k.shareRoute.gamefication.internal.archetype.mapper.ArchetypeModelMapper;
import com.hush0k.shareRoute.gamefication.internal.archetype.domain.ArchetypeModel;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.ArchetypeModelCreate;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.response.ArchetypeModelResponse;
import com.hush0k.shareRoute.gamefication.internal.archetype.repository.ArchetypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ArchetypeModelService {

    private final ArchetypeRepository archetypeRepository;
    private final ArchetypeModelMapper archetypeModelMapper;
    private final ArchetypeTranslationService archetypeTranslationService;

    private static final String DEFAULT_LANGUAGE_CODE = "en";


    public ArchetypeModelResponse createArchetypeModel(ArchetypeModelCreate dto){
        ArchetypeModel newArchetype = archetypeRepository.save(archetypeModelMapper.toArchetypeModel(dto));

        TranslationCreateDto translationCreateDto = TranslationCreateDto.builder()
                .archetypeModel(newArchetype)
                .description(dto.engDescription())
                .name(dto.engName())
                .languageCode(DEFAULT_LANGUAGE_CODE)
                .build();
        archetypeTranslationService.createTranslation(translationCreateDto);

        return archetypeModelMapper.toArchetypeModelResponse(newArchetype);
    }

    public ArchetypeModelResponse updateArchetypeModel(ArchetypeModelUpdateDto dto, Long archetypeId){
        ArchetypeModel archetypeModel = getArchetypeModel(archetypeId);

        archetypeModelMapper.updateArchetype(dto, archetypeModel);
        ArchetypeModel updatedArchetypeModel = archetypeRepository.save(archetypeModel);

        return archetypeModelMapper.toArchetypeModelResponse(updatedArchetypeModel);
    }

    public void deleteArchetypeModel(Long archetypeId){
        ArchetypeModel archetypeModel = getArchetypeModel(archetypeId);
        archetypeRepository.delete(archetypeModel);
    }

    @Transactional(readOnly = true)
    public ArchetypeModelResponse getArchetypeModelById(Long archetypeId){
        ArchetypeModel archetypeModel = getArchetypeModel(archetypeId);
        return archetypeModelMapper.toArchetypeModelResponse(archetypeModel);
    }

    @Transactional(readOnly = true)
    public PageResponse<ArchetypeModelResponse> getArchetypeModels(Pageable pageable){
        Page<ArchetypeModel> archetypeModels = archetypeRepository.findAll(pageable);
        Page<ArchetypeModelResponse> mapped = archetypeModels.map(archetypeModelMapper::toArchetypeModelResponse);
        return PageResponse.of(mapped);
    }

    public ArchetypeModelResponse publish(Long archetypeId){
        ArchetypeModel archetypeModel = getArchetypeModel(archetypeId);
        archetypeModel.setArchetypeState(ArchetypeState.PUBLISHED);
        return archetypeModelMapper.toArchetypeModelResponse(archetypeRepository.save(archetypeModel));
    }

    private ArchetypeModel getArchetypeModel(Long archetypeId){
        return archetypeRepository.findById(archetypeId).orElseThrow(
                () -> {
                    log.warn("Archetype with id {} not found", archetypeId);
                    return new ArchetypeModelNotFoundException(archetypeId);
                }
        );
    }

}
