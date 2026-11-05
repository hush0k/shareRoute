package com.hush0k.shareRoute.user;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
public record UserBriefDto(
        UUID id,
        String firstName,
        String lastName,
        String avatar,
        String nickname,
        Integer rankId
) {}
