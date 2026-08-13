package com.hush0k.shareRoute.auth.internal.mapper;

import com.hush0k.shareRoute.auth.internal.domins.Credentials;
import com.hush0k.shareRoute.auth.internal.dto.request.RegisterDataDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "hashedPassword", source = "hashedPassword")
    Credentials toCredentials(UUID userId, @Email @NotBlank String email, String hashedPassword);
}
