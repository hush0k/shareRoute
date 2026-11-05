package com.hush0k.shareRoute.auth.internal.exception;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

public class UserWithNicknameAlreadyExists extends MessageKeyException {
    public UserWithNicknameAlreadyExists(String nickname) {
        super("auth.nickname.alreadyExists", nickname);
    }
}
