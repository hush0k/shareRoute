package com.hush0k.shareRoute.user.internal.service;

import com.hush0k.shareRoute.user.internal.domain.FirstNameTranslation;
import com.hush0k.shareRoute.user.internal.domain.LastNameTranslation;
import com.hush0k.shareRoute.user.internal.repository.FirstNameTranslationRepository;
import com.hush0k.shareRoute.user.internal.repository.LastNameTranslationRepository;
import com.hush0k.shareRoute.user.internal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class RandomIdentityGenerator {

    private static final String DEFAULT_LANGUAGE = "en";
    private final FirstNameTranslationRepository firstNameRepository;
    private final LastNameTranslationRepository lastNameRepository;
    private final UserRepository userRepository;
    private final Random random  = new Random();

    public String generateFirstName(String languageCode) {
        long poolCount = firstNameRepository.countDistinctGroups();
        int randomId = random.nextInt((int) poolCount) + 1;

        return firstNameRepository
                .findByFirstNameGroupIdAndLanguageCode(randomId, languageCode)
                .or(() -> firstNameRepository
                        .findByFirstNameGroupIdAndLanguageCode(randomId, DEFAULT_LANGUAGE))
                .map(FirstNameTranslation::getValue)
                .orElse("User");
    }

    public String generateLastName(String languageCode) {
        long poolCount = lastNameRepository.countDistinctGroups();
        int randomId = random.nextInt((int) poolCount) + 1;

        return lastNameRepository
                .findByLastNameGroupIdAndLanguageCode(randomId, languageCode)
                .or(() -> lastNameRepository
                        .findByLastNameGroupIdAndLanguageCode(randomId, DEFAULT_LANGUAGE))
                .map(LastNameTranslation::getValue)
                .orElse(" ");
    }

    public String generateNickname() {
        String nickname;
        do {
            nickname = RandomStringUtils.secure().nextAlphanumeric(10);
        } while (userRepository.findByNickname(nickname).isPresent());
        return nickname;
    }

}
