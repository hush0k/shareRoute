package com.hush0k.shareRoute.followers.internal.dto.response;

import com.hush0k.shareRoute.user.UserBriefDto;
import org.springframework.data.domain.Page;

public record FollowingListDto(
        Page<UserBriefDto> followingList
) {
}
