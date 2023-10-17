package ru.neoflex.tariffs.services;

import ru.neoflex.tariffs.models.Tariff;

public interface KafkaProducer {
    void produceMessage(String topic, Tariff tariff);
}
