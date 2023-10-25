package ru.neoflex.tariffs.services;


import ru.neoflex.tariffs.models.Tariff;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AuditService {
    List<Tariff> getAllPreviousVersions(UUID id, Integer currentVersion);

    List<Tariff> getVersionForCertainPeriod(UUID id, LocalDate startDate, LocalDate endDate);

    Tariff revertToPreviousRevision(UUID id, Integer currentVersion);
}

