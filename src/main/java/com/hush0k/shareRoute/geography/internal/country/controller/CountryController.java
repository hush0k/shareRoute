package com.hush0k.shareRoute.geography.internal.country.controller;

import com.hush0k.shareRoute.common.exception.ErrorResponse;
import com.hush0k.shareRoute.common.global_dto.PageResponse;
import com.hush0k.shareRoute.geography.internal.country.dto.response.CountryResponseDto;
import com.hush0k.shareRoute.geography.internal.country.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
@Tag(name = "Countries", description = "Countries lookup")
public class CountryController {

    private final CountryService countryService;

    @GetMapping("/{countryId}")
    @Operation(summary = "Get a country by id")
    @ApiResponse(responseCode = "404", description = "Country not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<CountryResponseDto> getCountry(@PathVariable Integer countryId) {
        return ResponseEntity.ok(countryService.getCountryById(countryId));
    }

    @GetMapping
    @Operation(summary = "Get all countries")
    public ResponseEntity<PageResponse<CountryResponseDto>> getCountries(Pageable pageable) {
        return ResponseEntity.ok(countryService.getCountries(pageable));
    }
}
