package com.hush0k.shareRoute.user;

import com.hush0k.shareRoute.user.internal.domain.UserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserLookupPort {
    List<UserBriefDto> findBriefByUserId(List<UUID> userId);
    UserModel getUserById(UUID userId);
    Optional<UserModel> getUserByEmail(String email);
    UserModel getUserByNickname(String nickname);
}
