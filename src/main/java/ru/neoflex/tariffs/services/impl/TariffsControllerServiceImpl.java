package ru.neoflex.tariffs.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.neoflex.tariffs.dtos.TariffRequest;
import ru.neoflex.tariffs.dtos.TariffResponse;
import ru.neoflex.tariffs.exceptions.NotFoundException;
import ru.neoflex.tariffs.exceptions.TariffsException;
import ru.neoflex.tariffs.models.Tariff;
import ru.neoflex.tariffs.services.AuditService;
import ru.neoflex.tariffs.services.TariffService;
import ru.neoflex.tariffs.services.TariffsControllerService;
import ru.neoflex.tariffs.util.mappers.TariffMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, transactionManager = "jpaTransactionManager")
public class TariffsControllerServiceImpl implements TariffsControllerService {
    private final AuditService auditService;
    private final TariffService tariffService;
    private final TariffMapper tariffMapper;

    @Override
    public TariffResponse getCurrentVersion(UUID id) {
        Tariff tariff = tariffService.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Tariff with id %s not found", id)));

        return tariffMapper.tariffToTariffDto(tariff);
    }

    @Override
    @Transactional(transactionManager = "jpaTransactionManager")
    public void create(TariffRequest tariffRequest) {
        Tariff tariff = tariffMapper.tariffDtoToTariff(tariffRequest);

        String name = tariff.getName();
        Optional<Tariff> optionalTariff = tariffService.findByName(name);
        if (optionalTariff.isPresent()) {
            throw new TariffsException(String.format("Tariff with name %s already exists", name));
        }

        tariff.setVersion(1);

        tariffService.save(tariff);
    }

    @Override
    @Transactional(transactionManager = "jpaTransactionManager")
    public void update(UUID id, TariffRequest tariffRequest) {
        Tariff updatedTariff = tariffMapper.tariffDtoToTariff(tariffRequest);

        Tariff tariffFromDb = tariffService.findById(id)
                .orElseThrow(() -> new NotFoundException("Tariff with " + id + " not found"));

        updatedTariff.setId(id);
        updatedTariff.setVersion(tariffFromDb.getVersion());

        if (!tariffFromDb.equals(updatedTariff)) {
            updatedTariff.updateVersion();
        }

        tariffService.save(updatedTariff);
    }

    @Override
    @Transactional(transactionManager = "jpaTransactionManager")
    public void delete(UUID id) {
        Tariff tariff = tariffService.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Tariff with id %s not found", id)));

        tariffService.delete(tariff);
    }

    @Override
    public List<TariffResponse> getAllPreviousVersions(UUID id) {
        Integer currentVersion = tariffService.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Tariff with id %s not found", id)))
                .getVersion();

        List<Tariff> tariffs = auditService.getAllPreviousVersions(id, currentVersion);

        return tariffs.stream().map(tariffMapper::tariffToTariffDto).toList();
    }

    @Override
    public List<TariffResponse> getVersionForCertainPeriod(UUID id, LocalDate startDate, LocalDate endDate) {
        List<Tariff> tariffs = auditService.getVersionForCertainPeriod(id, startDate, endDate);
        return tariffs.stream().map(tariffMapper::tariffToTariffDto).toList();
    }

    @Override
    @Transactional(transactionManager = "jpaTransactionManager")
    public void rollBackVersion(UUID id) {
        Integer currentVersion = tariffService
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Tariff with id %s not found", id)))
                .getVersion();

        if (currentVersion != 1) {
            Tariff previousVersionOfTariff = auditService.revertToPreviousRevision(id, currentVersion);

            tariffService.update(id, previousVersionOfTariff);
        }
    }

}
