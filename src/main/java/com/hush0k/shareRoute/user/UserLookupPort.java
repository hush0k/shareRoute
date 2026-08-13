package com.hush0k.shareRoute.user;

import com.hush0k.shareRoute.user.internal.domain.UserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserLookupPort {
    List<UserBriefDto> findBriefByUserId(List<UUID> userId);
    UserBriefDto getUserById(UUID userId);
    boolean isEmailExists  (String email);
    UUID getUserIdByNickname(String nickname);
}
