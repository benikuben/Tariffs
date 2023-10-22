package ru.neoflex.tariffs.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.tariffs.dtos.ErrorResponse;
import ru.neoflex.tariffs.dtos.TariffRequest;
import ru.neoflex.tariffs.dtos.TariffResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TariffsAPI {
    @GetMapping("/{id}/current-version")
    @Operation(
            summary = "Find current version",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found current version successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TariffResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    ResponseEntity<TariffResponse> getCurrentVersion(@PathVariable("id") UUID id);

    @PostMapping
    @Operation(
            summary = "Create",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Created successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Already exists",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    ResponseEntity<Void> create(@RequestBody @Valid TariffRequest tariffRequest);

    @PatchMapping("/{id}")
    @Operation(
            summary = "Update",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Updated successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    ResponseEntity<Void> update(@PathVariable("id") UUID id, @RequestBody @Valid TariffRequest tariffRequest);

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Deleted successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    ResponseEntity<Void> delete(@PathVariable("id") UUID id);

    @GetMapping("/versions/{id}")
    @Operation(
            summary = "Find previous versions",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found previous versions successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TariffResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    ResponseEntity<List<TariffResponse>> getAllPreviousVersions(@PathVariable("id") UUID id);

    @GetMapping("/version/{id}/period")
    @Operation(
            summary = "Find version for certain period",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found version for certain period successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TariffResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    ResponseEntity<List<TariffResponse>> getVersionForCertainPeriod(@PathVariable("id") UUID id,
                                                                    @RequestParam("start_date")
                                                                           @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                                    @RequestParam("end_date")
                                                                           @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                                           LocalDate endDate);

    @PutMapping("/version/{id}/rollback")
    @Operation(
            summary = "Rollback version",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Rollback version successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    ResponseEntity<Void> rollBackVersion(@PathVariable("id") UUID id);
}
