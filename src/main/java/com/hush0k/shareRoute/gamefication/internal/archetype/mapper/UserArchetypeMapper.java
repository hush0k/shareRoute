package com.hush0k.shareRoute.gamefication.internal.archetype.mapper;

import com.hush0k.shareRoute.gamefication.internal.archetype.domain.Archetype;
import com.hush0k.shareRoute.gamefication.internal.archetype.domain.ArchetypeTranslation;
import com.hush0k.shareRoute.gamefication.internal.archetype.domain.UserArchetype;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.UserArchetypeCreateDto;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.UserArchetypeUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.response.ArchetypeSummaryDto;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.response.UserArchetypeResponseDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserArchetypeMapper {
    UserArchetype toUserArchetype(UserArchetypeCreateDto dto);

    @Mapping(source = "archetype", target = "archetype")
    UserArchetypeResponseDto toUserArchetypeResponseDto(UserArchetype userArchetype);

    @Mapping(target = "id", source = "userArchetype.id")
    @Mapping(target = "archetype", expression = "java(toArchetypeSummaryDto(userArchetype.getArchetype(), translation))")
    UserArchetypeResponseDto toUserArchetypeResponseDto(UserArchetype userArchetype,
                                                        ArchetypeTranslation translation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserArchetype(UserArchetypeUpdateDto dto, @MappingTarget UserArchetype userArchetype);

    default ArchetypeSummaryDto toArchetypeSummaryDto(Archetype archetype, ArchetypeTranslation translation) {
        if (archetype == null) {
            return null;
        }
        return ArchetypeSummaryDto.builder()
                .id(archetype.getId())
                .image(archetype.getImage())
                .archetypeState(archetype.getArchetypeState())
                .name(translation != null ? translation.getName() : null)
                .description(translation != null ? translation.getDescription() : null)
                .build();
    }
}
