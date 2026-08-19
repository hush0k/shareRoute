package com.hush0k.shareRoute.user.internal.repository;

import com.hush0k.shareRoute.user.internal.domain.LastNameTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LastNameTranslationRepository extends JpaRepository<LastNameTranslation, UUID> {
    Optional<LastNameTranslation> findByLastNameGroupIdAndLanguageCode(Integer lastNameGroupId,
                                                                              String languageCode);

    @Query("select count(distinct l.lastNameGroupId) from LastNameTranslation l")
    long countDistinctGroups();

}
