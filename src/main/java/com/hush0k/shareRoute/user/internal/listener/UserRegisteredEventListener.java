package com.hush0k.shareRoute.user.internal.listener;

import com.hush0k.shareRoute.auth.UserRegisteredEvent;
import com.hush0k.shareRoute.user.internal.service.UserService;
import com.hush0k.shareRoute.user.internal.dto.request.UserBootstrapDto;
import com.hush0k.shareRoute.user.internal.service.RandomIdentityGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserRegisteredEventListener {

    private static final int MAX_CREATE_ATTEMPTS = 3;

    private final UserService userService;
    private final RandomIdentityGenerator random;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserRegistered(UserRegisteredEvent event) {
        log.info("handleUserRegistered START for userId={}", event.userId());
        try {
            String nickname = event.nickname() != null
                    ? event.nickname()
                    : random.generateNickname();
            String firstName = event.firstName() != null
                    ? event.firstName()
                    : random.generateFirstName(event.languageCode());
            String lastName = event.lastName() != null
                    ? event.lastName()
                    : random.generateLastName(event.languageCode());
            log.info("last name: {}, first name: {}, nickname: {}", lastName, firstName, nickname);

            UserBootstrapDto dto = UserBootstrapDto.builder()
                    .id(event.userId())
                    .email(event.email())
                    .nickname(nickname)
                    .firstName(firstName)
                    .lastName(lastName)
                    .languageCode(event.languageCode())
                    .dateOfBirth(event.dateOfBirth())
                    .description(event.description())
                    .countryId(event.countryId())
                    .cityId(event.cityId())
                    .build();

            createWithNicknameFallback(dto);
            log.info("handleUserRegistered SUCCESS for userId={}", event.userId());
        } catch (Exception e) {
            log.error("handleUserRegistered FAILED for userId={}", event.userId(), e);
            throw e;
        }
    }

    // Nickname is checked in auth before commit, but another registration may take it
    // before the user row is created here. In that case fall back to a random nickname.
    private void createWithNicknameFallback(UserBootstrapDto dto) {
        UserBootstrapDto current = dto;
        for (int attempt = 1; ; attempt++) {
            try {
                userService.createBootstrapUser(current);
                return;
            } catch (DataIntegrityViolationException e) {
                if (attempt >= MAX_CREATE_ATTEMPTS || !userService.isNicknameTaken(current.nickname())) {
                    throw e;
                }
                String fallback = random.generateNickname();
                log.warn("Nickname {} was taken concurrently for userId={}, using {}",
                        current.nickname(), current.id(), fallback);
                current = current.toBuilder().nickname(fallback).build();
            }
        }
    }

}
