package com.hush0k.shareRoute.geography.internal.countryTranslation.repository;

import com.hush0k.shareRoute.geography.internal.countryTranslation.domain.CountryTranslation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryTranslationRepository extends JpaRepository<CountryTranslation, Integer> {
    @Query("select t from CountryTranslation t join fetch t.country " +
            "where t.country.id = :countryId and t.languageCode = :lang")
    Optional<CountryTranslation> findByCountryIdAndLanguageCode(@Param("countryId") Integer countryId,
                                                                @Param("lang") String languageCode);

    @Query(value = "select t from CountryTranslation t join fetch t.country where t.languageCode = :lang",
            countQuery = "select count(t) from CountryTranslation t where t.languageCode = :lang")
    Page<CountryTranslation> findAllByLang(@Param("lang") String languageCode, Pageable pageable);
}
