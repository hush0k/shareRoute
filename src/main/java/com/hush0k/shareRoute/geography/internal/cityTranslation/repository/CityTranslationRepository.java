package com.hush0k.shareRoute.geography.internal.cityTranslation.repository;

import com.hush0k.shareRoute.geography.internal.cityTranslation.domain.CityTranslation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityTranslationRepository extends JpaRepository<CityTranslation, Integer> {
    @Query("select t from CityTranslation t join fetch t.city " +
            "where t.city.id = :cityId and t.languageCode = :lang")
    Optional<CityTranslation> findByCityIdAndLanguageCode(@Param("cityId") Integer cityId,
                                                          @Param("lang") String languageCode);

    @Query(value = "select t from CityTranslation t join fetch t.city where t.languageCode = :lang",
            countQuery = "select count(t) from CityTranslation t where t.languageCode = :lang")
    Page<CityTranslation> findAllByLang(@Param("lang") String languageCode, Pageable pageable);

    @Query(value = "select t from CityTranslation t join fetch t.city c " +
            "where t.languageCode = :lang and c.country.id = :countryId",
            countQuery = "select count(t) from CityTranslation t " +
                    "where t.languageCode = :lang and t.city.country.id = :countryId")
    Page<CityTranslation> findAllByLangAndCountryId(@Param("lang") String languageCode,
                                                    @Param("countryId") Integer countryId,
                                                    Pageable pageable);
}
