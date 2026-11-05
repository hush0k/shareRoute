package com.hush0k.shareRoute.auth;

import java.time.LocalDate;
import java.util.UUID;

/**
 * nickname, firstName, lastName are optional: null means the user module generates a random value.
 * dateOfBirth, description, countryId, cityId are optional: null means the field stays empty.
 * countryId and cityId are already validated by the auth module.
 */
public record UserRegisteredEvent(
        UUID userId,
        String email,
        String languageCode,
        String nickname,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String description,
        Integer countryId,
        Integer cityId
) {}
