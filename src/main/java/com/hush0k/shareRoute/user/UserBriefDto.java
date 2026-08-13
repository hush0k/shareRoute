package com.hush0k.shareRoute.user;

import java.util.UUID;

public record UserBriefDto(
        UUID id,
        String firstName,
        String lastName,
        String avatar,
        String nickname,
        Integer rankId
) {}
