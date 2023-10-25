package ru.neoflex.tariffs.services;

import ru.neoflex.tariffs.models.Tariff;

import java.util.Optional;
import java.util.UUID;

public interface TariffService {
    Optional<Tariff> findById(UUID id);

    Optional<Tariff> findByName(String name);

    void save(Tariff tariff);

    void update(UUID id, Tariff tariff);

    void delete(Tariff tariff);
}

