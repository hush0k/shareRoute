package com.hush0k.shareRoute.gamefication.internal.archetype.exceptions;

import com.hush0k.shareRoute.common.exception.MessageKeyException;

public class ArchetypeNotFoundException extends MessageKeyException {
    public ArchetypeNotFoundException(Long archetypeId) {
        super("archetype.notFound", archetypeId);
    }
}
