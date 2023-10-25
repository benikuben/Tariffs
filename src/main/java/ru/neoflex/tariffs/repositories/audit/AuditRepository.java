package ru.neoflex.tariffs.repositories.audit;

import ru.neoflex.tariffs.models.Tariff;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AuditRepository {
    List<Tariff> getAllVersions(UUID id);

    List<Tariff> getVersionForCertainPeriod(UUID id, LocalDate startDate, LocalDate endDate);

    Tariff getPreviousVersion(UUID id, Integer currentVersion);

    void deleteCurrentVersion(UUID id, Integer currentVersion);
}
