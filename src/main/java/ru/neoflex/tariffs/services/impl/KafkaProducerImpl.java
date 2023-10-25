package ru.neoflex.tariffs.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.neoflex.tariffs.models.Tariff;
import ru.neoflex.tariffs.services.KafkaProducer;


@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducerImpl implements KafkaProducer {
    private final KafkaTemplate<Integer, Tariff> kafkaTemplate;

    @Override
    public void produceMessage(String topic, Tariff tariff) {
        kafkaTemplate.send(topic, tariff);
        log.info("Produced message {} {}", topic,  tariff);
    }
}

