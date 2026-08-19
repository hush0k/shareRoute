package com.hush0k.shareRoute.gamefication.internal.mapper;

import com.hush0k.shareRoute.gamefication.internal.domain.ArchetypeModel;
import com.hush0k.shareRoute.gamefication.internal.dto.request.ArchetypeModelCreate;
import com.hush0k.shareRoute.gamefication.internal.dto.request.ArchetypeModelUpdateDto;
import com.hush0k.shareRoute.gamefication.internal.dto.response.ArchetypeModelResponse;
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
