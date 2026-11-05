package com.hush0k.shareRoute.user;

import java.util.List;
import java.util.UUID;

public interface UserLookupPort {
    List<UserBriefDto> findBriefByUserId(List<UUID> userId);
    UserBriefDto getUserById(UUID userId);
    boolean isEmailExists  (String email);
    boolean isNicknameExists(String nickname);
    UUID getUserIdByNickname(String nickname);
}
