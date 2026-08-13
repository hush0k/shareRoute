package com.hush0k.shareRoute.auth.internal.exception;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

public class UserNotFoundByNickname extends MessageKeyException {
    public UserNotFoundByNickname(String nickname) {
        super("auth.notFound.nickname", nickname);
    }
}
