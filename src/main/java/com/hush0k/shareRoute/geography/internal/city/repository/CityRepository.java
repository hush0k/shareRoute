package com.hush0k.shareRoute.geography.internal.city.repository;

import com.hush0k.shareRoute.geography.internal.city.domain.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    Page<City> findAllByCountryId(Integer countryId, Pageable pageable);
}
