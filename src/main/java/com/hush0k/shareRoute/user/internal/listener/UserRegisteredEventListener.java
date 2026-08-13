package com.hush0k.shareRoute.user.internal.listener;

import com.hush0k.shareRoute.auth.UserRegisteredEvent;
import com.hush0k.shareRoute.user.internal.service.UserService;
import com.hush0k.shareRoute.user.internal.dto.request.UserBootstrapDto;
import com.hush0k.shareRoute.user.internal.service.RandomIdentityGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserRegisteredEventListener {

    private final UserService userService;
    private final RandomIdentityGenerator random;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserRegistered(UserRegisteredEvent event) {
        log.info("handleUserRegistered START for userId={}", event.userId());
        try {
            String nickname = random.generateNickname();
            String firstName = random.generateFirstName(event.languageCode());
            String lastName = random.generateLastName(event.languageCode());
            log.info("last name: {}, first name: {}, nickname: {}", lastName, firstName, nickname);

            UserBootstrapDto dto = UserBootstrapDto.builder()
                    .id(event.userId())
                    .email(event.email())
                    .nickname(nickname)
                    .firstName(firstName)
                    .lastName(lastName)
                    .languageCode(event.languageCode())
                    .build();

            userService.createBootstrapUser(dto);
            log.info("handleUserRegistered SUCCESS for userId={}", event.userId());
        } catch (Exception e) {
            log.error("handleUserRegistered FAILED for userId={}", event.userId(), e);
            throw e;
        }
    }

}
