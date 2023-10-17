package ru.neoflex.tariffs.controllers;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.tariffs.dtos.TariffRequest;
import ru.neoflex.tariffs.dtos.TariffResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TariffsAPI {
    @GetMapping("/{id}/current-version")
    ResponseEntity<TariffResponse> getCurrentVersion(@PathVariable("id") UUID id);

    @PostMapping()
    public ResponseEntity<Void> create(@RequestBody @Valid TariffRequest tariffRequest);

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") UUID id,
                                       @RequestBody @Valid TariffRequest tariffRequest);

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id);

    @GetMapping("/versions/{id}")
    public ResponseEntity<List<TariffResponse>> getAllPreviousVersions(@PathVariable("id") UUID id);

    @GetMapping("/version/{id}/period")
    public ResponseEntity<List<TariffResponse>> getVersionForCertainPeriod(@PathVariable("id") UUID id,
                                                                           @RequestParam("start_date")
                                                                   @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                                           @RequestParam("end_date")
                                                                   @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                                   LocalDate endDate);

    @PutMapping("/version/{id}/rollback")
    public ResponseEntity<Void> rollBackVersion(@PathVariable("id") UUID id);
}
