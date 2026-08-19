package com.hush0k.shareRoute.user.internal;

import com.hush0k.shareRoute.user.UserBriefDto;
import com.hush0k.shareRoute.user.internal.domain.User;
import com.hush0k.shareRoute.user.internal.dto.request.UserCreateDto;
import com.hush0k.shareRoute.user.internal.dto.request.UserUpdateDto;
import com.hush0k.shareRoute.user.internal.dto.response.UserResponseDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreateDto dto);

    User toUser(UserResponseDto dto);

    UserResponseDto toUserResponseDto(User user);

    List<UserResponseDto> toUserResponseDtoList(List<User> users);

    UserBriefDto toUserBriefDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserUpdateDto dto, @MappingTarget User user);
}