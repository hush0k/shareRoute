package com.hush0k.shareRoute.user.internal.repository;

import com.hush0k.shareRoute.user.internal.domain.FirstNameTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FirstNameTranslationRepository extends JpaRepository<FirstNameTranslation, UUID> {
    Optional<FirstNameTranslation> findByFirstNameGroupIdAndLanguageCode(Integer firstNameGroupId,
                                                                              String languageCode);

    @Query("select count(distinct f.firstNameGroupId) from FirstNameTranslation f")
    long countDistinctGroups();

}
