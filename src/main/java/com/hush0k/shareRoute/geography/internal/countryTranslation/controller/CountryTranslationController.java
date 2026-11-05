package com.hush0k.shareRoute.geography.internal.countryTranslation.controller;

import com.hush0k.shareRoute.common.exception.ErrorResponse;
import com.hush0k.shareRoute.common.global_dto.PageResponse;
import com.hush0k.shareRoute.geography.internal.countryTranslation.dto.response.CountryTranslationResponseDto;
import com.hush0k.shareRoute.geography.internal.countryTranslation.service.CountryTranslationService;
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
@Tag(name = "Country translations", description = "Translations of countries")
public class CountryTranslationController {

    private final CountryTranslationService countryTranslationService;

    @GetMapping("/{countryId}/translations/{languageCode}")
    @Operation(summary = "Get a translation by country id and language code")
    @ApiResponse(responseCode = "404", description = "Translation not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<CountryTranslationResponseDto> getCountryTranslation(
            @PathVariable Integer countryId,
            @PathVariable String languageCode) {
        return ResponseEntity.ok(countryTranslationService.findByIdCountryIdAndCountryCode(countryId, languageCode));
    }

    @GetMapping("/translations")
    @Operation(summary = "Get country translations by language")
    public ResponseEntity<PageResponse<CountryTranslationResponseDto>> getCountryTranslations(
            @RequestParam String language,
            Pageable pageable) {
        return ResponseEntity.ok(countryTranslationService.getCountryTranslations(language, pageable));
    }
}
