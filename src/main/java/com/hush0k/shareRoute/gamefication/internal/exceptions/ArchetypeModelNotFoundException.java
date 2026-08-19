package com.hush0k.shareRoute.gamefication.internal.exceptions;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

public class ArchetypeModelNotFoundException extends MessageKeyException {
    public ArchetypeModelNotFoundException(Long archetypeId) {
        super("archetype.notFound", archetypeId);
    }
}
