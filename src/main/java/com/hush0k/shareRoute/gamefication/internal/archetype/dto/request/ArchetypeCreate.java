package com.hush0k.shareRoute.gamefication.internal.archetype.dto.request;

import jakarta.validation.constraints.Size;

public record ArchetypeCreate(

        String image,

        @Size(min=3, max=800)
        String engDescription,

        @Size(min=3, max=200)
        String engName

) {}
