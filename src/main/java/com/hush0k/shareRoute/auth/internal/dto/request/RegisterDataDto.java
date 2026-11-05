package com.hush0k.shareRoute.auth.internal.dto.request;

import com.hush0k.shareRoute.auth.internal.annotations.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@PasswordMatches
public record RegisterDataDto(

        @Email
        @NotBlank
        String email,

        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9\\s]).{8,72}$",
                message = "auth.password.invalid")
        String password,

        String confirmPassword,

        @Size(min = 2, max = 64, message = "{auth.nickname.size}")
        String nickname,

        @Size(min = 2, max = 64, message = "{auth.firstName.size}")
        String firstName,

        @Size(min = 2, max = 64, message = "{auth.lastName.size}")
        String lastName,

        @Past(message = "{auth.dateOfBirth.past}")
        LocalDate dateOfBirth,

        @Size(max = 1000, message = "{auth.description.size}")
        String description,

        @Positive
        Integer countryId,

        @Positive
        Integer cityId
) {}
