package com.hush0k.shareRoute.geography.internal.country.repository;

import com.hush0k.shareRoute.geography.internal.country.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
}
