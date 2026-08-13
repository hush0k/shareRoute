package com.hush0k.shareRoute.auth.internal.service;

import com.hush0k.shareRoute.auth.UserRegisteredEvent;
import com.hush0k.shareRoute.auth.internal.domins.Credentials;
import com.hush0k.shareRoute.auth.internal.dto.request.UserCreateDto;
import com.hush0k.shareRoute.auth.internal.exception.UserWithEmailAlreadyExists;
import com.hush0k.shareRoute.auth.internal.dto.request.RegisterDataDto;
import com.hush0k.shareRoute.auth.internal.mapper.CredentialsMapper;
import com.hush0k.shareRoute.auth.internal.repository.CredentialsRepository;
import com.hush0k.shareRoute.auth.internal.repository.LoginAttemptRepository;
import com.hush0k.shareRoute.auth.internal.repository.RefreshRepository;
import com.hush0k.shareRoute.user.UserLookupPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

    private final LoginAttemptRepository loginAttemptRepository;
    private final CredentialsRepository credentialsRepository;
    private final RefreshRepository refreshRepository;
    private final UserLookupPort userService;
    private final PasswordEncoder passwordEncoder;
    private final CredentialsMapper credentialsMapper;
    private final ApplicationEventPublisher eventPublisher;

    public void register(RegisterDataDto dto, String languageCode) {
        if (userService.getUserByEmail(dto.email()).isPresent()) {
            throw new UserWithEmailAlreadyExists(dto.email());
        }

        String hashedPassword = passwordEncoder.encode(dto.password());
        UUID userId = UUID.randomUUID();

        Credentials userCredentials = credentialsMapper.toCredentials(userId, dto.email(), hashedPassword);
        credentialsRepository.save(userCredentials);

        eventPublisher.publishEvent(new UserRegisteredEvent(userId, dto.email(), languageCode));
    }



}
