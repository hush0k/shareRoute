package com.hush0k.shareRoute.followers.internal;

import com.hush0k.shareRoute.followers.internal.dto.request.FollowersCreateDto;
import com.hush0k.shareRoute.followers.internal.dto.response.FollowersResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FollowersMapper {
    Followers toFollowers(FollowersCreateDto followers);
    FollowersResponseDto toFollowersResponseDto(Followers followers);
}
