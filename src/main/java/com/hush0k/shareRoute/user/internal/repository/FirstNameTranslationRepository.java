package com.hush0k.shareRoute.user.internal.repository;

import com.hush0k.shareRoute.user.internal.domain.FirstNameTranslationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FirstNameTranslationRepository extends JpaRepository<FirstNameTranslationModel, UUID> {
    Optional<FirstNameTranslationModel> findByFirstNameGroupIdAndLanguageCode(Integer firstNameGroupId,
                                                                              String languageCode);

    @Query("select count(distinct f.firstNameGroupId) from FirstNameTranslationModel f")
    long countDistinctGroups();

}
