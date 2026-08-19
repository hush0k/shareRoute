package com.hush0k.shareRoute.gamefication.internal.mapper;

import com.hush0k.shareRoute.gamefication.internal.domain.ArchetypeModel;
import com.hush0k.shareRoute.gamefication.internal.domain.ArchetypeTranslationModel;
import com.hush0k.shareRoute.gamefication.internal.dto.request.ArchetypeModelUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.dto.request.TranslationCreateDto;
import com.hush0k.shareRoute.gamefication.internal.dto.request.TranslationUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.dto.response.TranslationResponseDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TranslationMapper {
    @Mapping(source = "archetypeModel", target = "archetype")
    ArchetypeTranslationModel toArchetypeTranslationModel(TranslationCreateDto dto);

    @Mapping(source = "archetype", target = "archetypeModel")
    TranslationResponseDto toTranslationResponseDto(ArchetypeTranslationModel archetypeTranslationModel);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTranslation(TranslationUpdateDto dto, @MappingTarget ArchetypeTranslationModel translationModel);
}
