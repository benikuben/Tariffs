package ru.neoflex.tariffs.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.neoflex.tariffs.dtos.TariffRequest;
import ru.neoflex.tariffs.dtos.TariffResponse;
import ru.neoflex.tariffs.services.TariffsControllerService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tariffs")
public class TariffsController implements TariffsAPI{
    private final TariffsControllerService tariffsControllerService;

    @Override
    public ResponseEntity<TariffResponse> getCurrentVersion(UUID id) {
        return new ResponseEntity<>(tariffsControllerService.getCurrentVersion(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> create(TariffRequest tariffRequest) {
        tariffsControllerService.create(tariffRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> update(UUID id, TariffRequest tariffRequest) {
        tariffsControllerService.update(id, tariffRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(UUID id) {
        tariffsControllerService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TariffResponse>> getAllPreviousVersions(UUID id) {
        return new ResponseEntity<>(tariffsControllerService.getAllPreviousVersions(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TariffResponse>> getVersionForCertainPeriod(UUID id, LocalDate startDate, LocalDate endDate) {
        return new ResponseEntity<>(tariffsControllerService.getVersionForCertainPeriod(id, startDate, endDate), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> rollBackVersion(UUID id) {
        tariffsControllerService.rollBackVersion(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
