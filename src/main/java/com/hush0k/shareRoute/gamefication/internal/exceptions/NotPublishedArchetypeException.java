package com.hush0k.shareRoute.gamefication.internal.exceptions;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

public class NotPublishedArchetypeException extends MessageKeyException {
    public NotPublishedArchetypeException(Long archetypeId) {
        super("archetype.notPublished", archetypeId);
    }
}