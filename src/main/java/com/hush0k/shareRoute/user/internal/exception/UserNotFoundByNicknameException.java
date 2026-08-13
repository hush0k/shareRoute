package com.hush0k.shareRoute.user.internal.exception;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

public class UserNotFoundByNicknameException extends MessageKeyException {
    public UserNotFoundByNicknameException(String nickname) {
        super("user.notFound.byNickname", nickname);
    }
}