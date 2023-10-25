package ru.neoflex.tariffs.services;

import ru.neoflex.tariffs.dtos.TariffRequest;
import ru.neoflex.tariffs.dtos.TariffResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TariffsControllerService {
    TariffResponse getCurrentVersion(UUID id);

    void create(TariffRequest tariffRequest);

    void update(UUID id, TariffRequest tariffRequest);

    void delete(UUID id);

    List<TariffResponse> getAllPreviousVersions(UUID id);

    List<TariffResponse> getVersionForCertainPeriod(UUID id, LocalDate startDate, LocalDate endDate);

    void rollBackVersion(UUID id);
}
