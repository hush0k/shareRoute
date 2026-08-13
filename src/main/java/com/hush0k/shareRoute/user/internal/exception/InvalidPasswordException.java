package com.hush0k.shareRoute.user.internal.exception;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

public class InvalidPasswordException extends MessageKeyException {
    public InvalidPasswordException() {
        super("user.password.invalid");
    }
}