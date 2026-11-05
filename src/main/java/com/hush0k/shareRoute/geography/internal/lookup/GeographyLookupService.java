package com.hush0k.shareRoute.geography.internal.lookup;

import com.hush0k.shareRoute.geography.GeographyLookupPort;
import com.hush0k.shareRoute.geography.internal.city.domain.City;
import com.hush0k.shareRoute.geography.internal.city.exceptions.CityCountryMismatchException;
import com.hush0k.shareRoute.geography.internal.city.exceptions.CityNotFoundException;
import com.hush0k.shareRoute.geography.internal.city.repository.CityRepository;
import com.hush0k.shareRoute.geography.internal.country.exceptions.CountryNotFoundException;
import com.hush0k.shareRoute.geography.internal.country.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GeographyLookupService implements GeographyLookupPort {

    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;

    @Override
    public Integer resolveCountryId(Integer countryId, Integer cityId) {
        if (countryId != null && !countryRepository.existsById(countryId)) {
            throw new CountryNotFoundException(countryId);
        }
        if (cityId == null) {
            return countryId;
        }

        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
        Integer cityCountryId = city.getCountry().getId();
        if (countryId != null && !countryId.equals(cityCountryId)) {
            throw new CityCountryMismatchException(cityId, countryId);
        }
        return cityCountryId;
    }
}
