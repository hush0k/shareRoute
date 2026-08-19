package com.hush0k.shareRoute.gamefication.internal.exceptions;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

public class UserArchetypeNotFoundException extends MessageKeyException {
    public UserArchetypeNotFoundException(Long userArchetypeId) {
        super("archetype.user.notFound", userArchetypeId);
    }
}
