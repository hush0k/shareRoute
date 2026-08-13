package com.hush0k.shareRoute.auth.internal.exception;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

public class NotMatchPasswordsException extends MessageKeyException {
    public NotMatchPasswordsException() {
        super("auth.passwords.notMatch");
    }
}
