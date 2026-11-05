package com.hush0k.shareRoute.post.internal.mapper;

import com.hush0k.shareRoute.post.internal.domain.Post;
import com.hush0k.shareRoute.post.internal.dto.request.PostCreateDto;
import com.hush0k.shareRoute.post.internal.dto.request.PostUpdateDto;
import com.hush0k.shareRoute.post.internal.dto.response.PostResponseDto;
import com.hush0k.shareRoute.user.UserBriefDto;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "id", source = "post.id")
    @Mapping(target = "user", source = "user")
    PostResponseDto toPostResponseDto(Post post, UserBriefDto user);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sharedCount", ignore = true)
    @Mapping(target = "exp", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Post toPost(PostCreateDto postCreateDto, UUID userId);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePostFromDto(PostUpdateDto dto, @MappingTarget Post post);
}
