package ru.neoflex.tariffs.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.neoflex.tariffs.dtos.TariffRequest;
import ru.neoflex.tariffs.exceptions.NotFoundException;
import ru.neoflex.tariffs.exceptions.TariffsException;
import ru.neoflex.tariffs.models.Tariff;
import ru.neoflex.tariffs.services.impl.TariffsControllerServiceImpl;
import ru.neoflex.tariffs.util.mappers.TariffMapper;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TariffsControllerServiceTest {
    @Mock
    private TariffService tariffService;
    @Mock
    private TariffMapper tariffMapper;
    @InjectMocks
    private TariffsControllerServiceImpl tariffsControllerService;

    @Test
    void getCurrentVersion() {
        when(tariffService.findById(any()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> tariffsControllerService.getCurrentVersion(UUID.randomUUID()));
    }

    @Test
    void create() {
        when(tariffMapper.tariffDtoToTariff(any()))
                .thenReturn(new Tariff());

        when(tariffService.findByName(any()))
                .thenReturn(Optional.of(
                        new Tariff()
                ));

        assertThrows(TariffsException.class, () -> tariffsControllerService.create(new TariffRequest()));
    }

    @Test
    void update() {
        when(tariffMapper.tariffDtoToTariff(any()))
                .thenReturn(new Tariff());

        when(tariffService.findById(any()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> tariffsControllerService.update(
                UUID.randomUUID(), new TariffRequest()
        ));
    }

    @Test
    void delete() {
        when(tariffService.findById(any()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> tariffsControllerService.delete(UUID.randomUUID()));
    }

    @Test
    void getAllPreviousVersions() {
        when(tariffService.findById(any()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> tariffsControllerService.getAllPreviousVersions(UUID.randomUUID()));
    }

    @Test
    void getVersionForCertainPeriod() {
        when(tariffService.findById(any()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> tariffsControllerService.getVersionForCertainPeriod(
                UUID.randomUUID(),
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2000, 2, 1)));
    }

    @Test
    void rollBackVersion() {
        when(tariffService.findById(any()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> tariffsControllerService.rollBackVersion(UUID.randomUUID()));
    }
}