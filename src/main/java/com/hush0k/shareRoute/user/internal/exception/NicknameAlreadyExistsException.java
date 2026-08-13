package com.hush0k.shareRoute.user.internal.exception;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

public class NicknameAlreadyExistsException extends MessageKeyException {
    public NicknameAlreadyExistsException(String nickname) {
        super("user.nickname.alreadyExists", nickname);
    }
}