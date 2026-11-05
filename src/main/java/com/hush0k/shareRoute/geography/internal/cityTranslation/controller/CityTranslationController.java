package com.hush0k.shareRoute.geography.internal.cityTranslation.controller;

import com.hush0k.shareRoute.common.exception.ErrorResponse;
import com.hush0k.shareRoute.common.global_dto.PageResponse;
import com.hush0k.shareRoute.geography.internal.cityTranslation.dto.response.CityTranslationResponseDto;
import com.hush0k.shareRoute.geography.internal.cityTranslation.service.CityTranslationService;
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
@RequestMapping("/api/cities")
@RequiredArgsConstructor
@Tag(name = "City translations", description = "Translations of cities")
public class CityTranslationController {

    private final CityTranslationService cityTranslationService;

    @GetMapping("/{cityId}/translations/{languageCode}")
    @Operation(summary = "Get a translation by city id and language code")
    @ApiResponse(responseCode = "404", description = "Translation not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<CityTranslationResponseDto> getCityTranslation(
            @PathVariable Integer cityId,
            @PathVariable String languageCode) {
        return ResponseEntity.ok(cityTranslationService.findByCityIdAndLanguageCode(cityId, languageCode));
    }

    @GetMapping("/translations")
    @Operation(summary = "Get city translations by language, optionally filtered by country")
    public ResponseEntity<PageResponse<CityTranslationResponseDto>> getCityTranslations(
            @RequestParam String language,
            @RequestParam(required = false) Integer countryId,
            Pageable pageable) {
        return ResponseEntity.ok(cityTranslationService.getCityTranslations(language, countryId, pageable));
    }
}
