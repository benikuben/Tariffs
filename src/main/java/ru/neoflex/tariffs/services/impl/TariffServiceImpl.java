package ru.neoflex.tariffs.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.neoflex.tariffs.models.Tariff;
import ru.neoflex.tariffs.repositories.TariffRepository;
import ru.neoflex.tariffs.services.KafkaProducer;
import ru.neoflex.tariffs.services.TariffService;

import java.util.Optional;
import java.util.UUID;

import static ru.neoflex.tariffs.config.KafkaTopics.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, transactionManager = "jpaTransactionManager")
public class TariffServiceImpl implements TariffService {
    private final TariffRepository tariffRepository;
    private final KafkaProducer kafkaProducer;

    @Override
    public Optional<Tariff> findById(UUID id) {
        log.info("Find tariff by id {}", id);
        Optional<Tariff> tariff = tariffRepository.findById(id);
        log.info("Found tariff {}", tariff);
        return tariff;
    }

    @Override
    public Optional<Tariff> findByName(String name) {
        log.info("Find tariff by name {}", name);
        Optional<Tariff> tariff = tariffRepository.findByName(name);
        log.info("Found tariff {}", tariff);
        return tariff;
    }

    @Override
    @Transactional(transactionManager = "jpaTransactionManager")
    public void save(Tariff tariff) {
        tariffRepository.save(tariff);
        log.info("Saved tariff {}", tariff);
        kafkaProducer.produceMessage(CREATE_TARIFF, tariff);
    }

    @Override
    @Transactional(transactionManager = "jpaTransactionManager")
    public void update(UUID id, Tariff updatedTariff) {
        updatedTariff.setId(id);
        tariffRepository.save(updatedTariff);
        log.info("Updated tariff {}", updatedTariff);
        kafkaProducer.produceMessage(UPDATE_TARIFF, updatedTariff);
    }

    @Override
    @Transactional(transactionManager = "jpaTransactionManager")
    public void delete(Tariff tariff) {
        tariffRepository.delete(tariff);
        log.info("Deleted tariff {}", tariff);
        kafkaProducer.produceMessage(DELETE_TARIFF, tariff);
    }
}
