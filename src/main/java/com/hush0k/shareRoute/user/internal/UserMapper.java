package com.hush0k.shareRoute.user.internal;

import com.hush0k.shareRoute.user.UserBriefDto;
import com.hush0k.shareRoute.user.internal.domain.UserModel;
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

    UserModel toUserModel(UserCreateDto dto);

    UserModel toUserModel(UserResponseDto dto);

    UserResponseDto toUserResponseDto(UserModel user);

    List<UserResponseDto> toUserResponseDtoList(List<UserModel> users);

    UserBriefDto toUserBriefDto(UserModel user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserUpdateDto dto, @MappingTarget UserModel user);
}