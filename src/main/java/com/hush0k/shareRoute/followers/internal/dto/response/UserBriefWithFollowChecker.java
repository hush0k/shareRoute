package com.hush0k.shareRoute.followers.internal.dto.response;

import java.util.UUID;

public record UserBriefWithFollowChecker(
        UUID id,
        String firstName,
        String lastName,
        String nickname,
        String avatar,
        Integer rankId,
        boolean isFollow
) {
}
