package com.hush0k.shareRoute.user.internal.repository;

import com.hush0k.shareRoute.user.internal.domain.LastNameTranslationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LastNameTranslationRepository extends JpaRepository<LastNameTranslationModel, UUID> {
    Optional<LastNameTranslationModel> findByLastNameGroupIdAndLanguageCode(Integer lastNameGroupId,
                                                                              String languageCode);

    @Query("select count(distinct l.lastNameGroupId) from LastNameTranslationModel l")
    long countDistinctGroups();

}
