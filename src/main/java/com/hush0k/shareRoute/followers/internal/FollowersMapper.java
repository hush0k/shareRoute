package com.hush0k.shareRoute.followers.internal;

import com.hush0k.shareRoute.followers.internal.dto.request.FollowersCreateDto;
import com.hush0k.shareRoute.followers.internal.dto.response.FollowersResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FollowersMapper {
    FollowersModel toFollowersModel(FollowersCreateDto followersModel);
    FollowersResponseDto toFollowersResponseDto(FollowersModel followersModel);
}
