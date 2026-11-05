package com.hush0k.shareRoute.auth.internal.service;

import com.hush0k.shareRoute.auth.UserRegisteredEvent;
import com.hush0k.shareRoute.auth.internal.domins.Credentials;
import com.hush0k.shareRoute.auth.internal.domins.LoginAttempt;
import com.hush0k.shareRoute.auth.internal.domins.RefreshToken;
import com.hush0k.shareRoute.auth.internal.dto.request.LoginDataDto;
import com.hush0k.shareRoute.auth.internal.dto.response.TokenResponseDto;
import com.hush0k.shareRoute.auth.internal.exception.InvalidCredentialsExceptions;
import com.hush0k.shareRoute.auth.internal.exception.TooManyLoginAttemptsException;
import com.hush0k.shareRoute.auth.internal.exception.UserWithEmailAlreadyExists;
import com.hush0k.shareRoute.auth.internal.exception.UserWithNicknameAlreadyExists;
import com.hush0k.shareRoute.auth.internal.dto.request.RegisterDataDto;
import com.hush0k.shareRoute.auth.internal.mapper.CredentialsMapper;
import com.hush0k.shareRoute.auth.internal.repository.CredentialsRepository;
import com.hush0k.shareRoute.auth.internal.repository.LoginAttemptRepository;
import com.hush0k.shareRoute.auth.internal.repository.RefreshRepository;
import com.hush0k.shareRoute.geography.GeographyLookupPort;
import com.hush0k.shareRoute.user.UserLookupPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
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
    private final GeographyLookupPort geographyLookup;
    private final PasswordEncoder passwordEncoder;
    private final CredentialsMapper credentialsMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    private final LoginAttemptService loginAttemptService;
    private final TokenBlacklistService blacklistService;

    public void register(RegisterDataDto dto, String languageCode) {
        if (userService.isEmailExists(dto.email())) {
            throw new UserWithEmailAlreadyExists(dto.email());
        }

        String nickname = blankToNull(dto.nickname());
        if (nickname != null && userService.isNicknameExists(nickname)) {
            throw new UserWithNicknameAlreadyExists(nickname);
        }

        Integer countryId = geographyLookup.resolveCountryId(dto.countryId(), dto.cityId());

        String hashedPassword = passwordEncoder.encode(dto.password());
        UUID userId = UUID.randomUUID();

        Credentials userCredentials = credentialsMapper.toCredentials(userId, dto.email(), hashedPassword);
        credentialsRepository.save(userCredentials);

        eventPublisher.publishEvent(new UserRegisteredEvent(
                userId,
                dto.email(),
                languageCode,
                nickname,
                blankToNull(dto.firstName()),
                blankToNull(dto.lastName()),
                dto.dateOfBirth(),
                blankToNull(dto.description()),
                countryId,
                dto.cityId()
        ));
    }

    private static String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.strip();
    }

    public TokenResponseDto login(LoginDataDto dto, String languageCode, String ipAddress) {

        if (loginAttemptService.isBlocked(dto.username())) {
            log.info("User has been blocked.");
            throw new TooManyLoginAttemptsException();
        }

        CustomUserDetails userDetails;

        try {
            userDetails = customUserDetailsService.loadUserByUsername(dto.username());
        } catch (UsernameNotFoundException e) {
            loginAttemptService.registerFailure(dto.username());
            logAttempt(null, ipAddress, false);
            throw new InvalidCredentialsExceptions();
        }

        if (!passwordEncoder.matches(dto.password(), userDetails.getPassword())) {
            loginAttemptService.registerFailure(dto.username());
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

        loginAttemptService.registerSuccess(dto.username());
        logAttempt(userDetails.getUserId(), ipAddress, true);

        return new TokenResponseDto(accessToken, refreshToken);

    }

    public TokenResponseDto getAccessTokenByRefreshToken(String refreshToken) {
        if (!jwtService.isRefreshToken(refreshToken) || !jwtService.isTokenValid(refreshToken)) {
            throw new InvalidCredentialsExceptions();
        }

        String tokenHash = hashToken(refreshToken);

        RefreshToken storedToken = refreshRepository.findByTokenHash(tokenHash)
                .orElseThrow(InvalidCredentialsExceptions::new);

        if (Boolean.TRUE.equals(storedToken.getIsRevoked()) || storedToken.getExpiresAt().isBefore(Instant.now())) {
            throw new InvalidCredentialsExceptions();
        }

        Credentials userCredentials = credentialsRepository.findByUserId(storedToken.getUserId())
                .orElseThrow(InvalidCredentialsExceptions::new);

        storedToken.setIsRevoked(true);
        refreshRepository.save(storedToken);

        String accessTokenNew = jwtService.generateAccessToken(userCredentials.getUserId(), userCredentials.getRole());
        String refreshTokenNew = jwtService.generateRefreshToken(userCredentials.getUserId());

        RefreshToken newRefreshTokenEntity = RefreshToken.builder()
                .userId(userCredentials.getUserId())
                .tokenHash(hashToken(refreshTokenNew))
                .expiresAt(jwtService.extractExpiration(refreshTokenNew))
                .isRevoked(false)
                .build();
        refreshRepository.save(newRefreshTokenEntity);

        return new TokenResponseDto(accessTokenNew, refreshTokenNew);

    }

    public void logout(String accessToken, String refreshToken) {
        String tokenHash = hashToken(refreshToken);
        refreshRepository.findByTokenHash(tokenHash).ifPresent(rt -> {
            rt.setIsRevoked(true);
            refreshRepository.save(rt);
        });

        blacklistService.blacklist(accessToken);
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
