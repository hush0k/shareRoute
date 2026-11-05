package com.hush0k.shareRoute.geography;

public interface GeographyLookupPort {
    /**
     * Validates that the country and city exist and that the city belongs to the country.
     * If only cityId is given, the country is taken from the city.
     *
     * @return the resolved country id, or null if both arguments are null
     */
    Integer resolveCountryId(Integer countryId, Integer cityId);
}
