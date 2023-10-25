package ru.neoflex.tariffs.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.neoflex.tariffs.models.Tariff;
import ru.neoflex.tariffs.repositories.audit.AuditRepository;
import ru.neoflex.tariffs.services.AuditService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, transactionManager = "jpaTransactionManager")
public class AuditServiceImpl implements AuditService {
    private final AuditRepository auditRepository;

    @Override
    public List<Tariff> getAllPreviousVersions(UUID id, Integer currentVersion) {
        log.info("Get all previous versions of tariff with id {} and currentVersion {}", id, currentVersion);
        List<Tariff> tariffs = auditRepository.getAllVersions(id).stream()
                .filter(t -> t.getVersion() < currentVersion)
                .toList();
        log.info("Previous versions {}", tariffs);
        return tariffs;
    }

    @Override
    public List<Tariff> getVersionForCertainPeriod(UUID id, LocalDate startDate, LocalDate endDate) {
        log.info("Get all versions of tariff with id {} from {} to {}", id, startDate, endDate);
        List<Tariff> tariffs=  auditRepository.getVersionForCertainPeriod(id, startDate, endDate);
        log.info("Versions {}", tariffs);
        return tariffs;
    }

    @Override
    @Transactional(transactionManager = "jpaTransactionManager")
    public Tariff revertToPreviousRevision(UUID id, Integer currentVersion) {
        log.info("Revert to previous revision of tariff with id {} and currentVersion {}", id, currentVersion);
        Tariff previousVersion = auditRepository.getPreviousVersion(id, currentVersion);
        log.info("Previous version {}", previousVersion);
        log.info("Delete current version of tariff with id {} and currentVersion {}", id, currentVersion);
        auditRepository.deleteCurrentVersion(id, currentVersion);
        return previousVersion;
    }


}
