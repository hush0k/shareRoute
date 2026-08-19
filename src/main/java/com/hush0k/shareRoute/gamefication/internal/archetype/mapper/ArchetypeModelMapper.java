package com.hush0k.shareRoute.gamefication.internal.archetype.mapper;

import com.hush0k.shareRoute.gamefication.internal.archetype.domain.ArchetypeModel;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.ArchetypeModelCreate;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.request.ArchetypeModelUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.archetype.dto.response.ArchetypeModelResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ArchetypeModelMapper {

    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE)
    ArchetypeModel toArchetypeModel(ArchetypeModelCreate dto);

    ArchetypeModelResponse toArchetypeModelResponse(ArchetypeModel dto);
    ArchetypeModel toArchetypeFromResponse(ArchetypeModelResponse dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateArchetype(ArchetypeModelUpdateDto dto, @MappingTarget ArchetypeModel archetypeModel);

}
