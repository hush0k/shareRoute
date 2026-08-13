package com.hush0k.shareRoute.auth.internal.service;

import com.hush0k.shareRoute.auth.UserRegisteredEvent;
import com.hush0k.shareRoute.auth.internal.domins.Credentials;
import com.hush0k.shareRoute.auth.internal.domins.LoginAttempt;
import com.hush0k.shareRoute.auth.internal.domins.RefreshToken;
import com.hush0k.shareRoute.auth.internal.dto.request.LoginDataDto;
import com.hush0k.shareRoute.auth.internal.dto.response.TokenResponseDto;
import com.hush0k.shareRoute.auth.internal.exception.InvalidCredentialsExceptions;
import com.hush0k.shareRoute.auth.internal.exception.UserWithEmailAlreadyExists;
import com.hush0k.shareRoute.auth.internal.dto.request.RegisterDataDto;
import com.hush0k.shareRoute.auth.internal.mapper.CredentialsMapper;
import com.hush0k.shareRoute.auth.internal.repository.CredentialsRepository;
import com.hush0k.shareRoute.auth.internal.repository.LoginAttemptRepository;
import com.hush0k.shareRoute.auth.internal.repository.RefreshRepository;
import com.hush0k.shareRoute.user.UserLookupPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
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
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;

    public void register(RegisterDataDto dto, String languageCode) {
        if (userService.isEmailExists(dto.email())) {
            throw new UserWithEmailAlreadyExists(dto.email());
        }

        String hashedPassword = passwordEncoder.encode(dto.password());
        UUID userId = UUID.randomUUID();

        Credentials userCredentials = credentialsMapper.toCredentials(userId, dto.email(), hashedPassword);
        credentialsRepository.save(userCredentials);

        eventPublisher.publishEvent(new UserRegisteredEvent(userId, dto.email(), languageCode));
    }

    public TokenResponseDto login(LoginDataDto dto, String languageCode, String ipAddress) {
        CustomUserDetails userDetails;

        try {
            userDetails = customUserDetailsService.loadUserByUsername(dto.username());
        } catch (UsernameNotFoundException e) {
            logAttempt(null, ipAddress, false);
            throw new InvalidCredentialsExceptions();
        }

        if (!passwordEncoder.matches(dto.password(), userDetails.getPassword())) {
            logAttempt(userDetails.getUserId(),  ipAddress, false);
            throw new InvalidCredentialsExceptions();
        }

        Credentials userCredentials = credentialsRepository.findByUserId(userDetails.getUserId())
                .orElseThrow(InvalidCredentialsExceptions::new);

        String accessToken = jwtService.generateAccessToken(userCredentials.getUserId(), userCredentials.getRole());
        String refreshToken = jwtService.generateRefreshToken(userCredentials.getUserId());

        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .userId(userDetails.getUserId())
                .tokenHash(hashToken(refreshToken))
                .expiresAt(jwtService.extractExpiration(refreshToken))
                .isRevoked(false)
                .build();
        refreshRepository.save(refreshTokenEntity);

        logAttempt(userDetails.getUserId(), ipAddress, true);

        return new TokenResponseDto(accessToken, refreshToken);

    }

    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }
    }

    private void logAttempt(UUID userId, String ipAddress, boolean isSuccessful) {
        LoginAttempt attempt = LoginAttempt.builder()
                .userId(userId)
                .ipAddress(ipAddress)
                .isSuccessful(isSuccessful)
                .build();
        loginAttemptRepository.save(attempt);
    }

}
