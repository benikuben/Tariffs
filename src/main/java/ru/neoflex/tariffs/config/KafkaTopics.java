package ru.neoflex.tariffs.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Profile(value = "local")
public class KafkaTopics {
    public static final String CREATE_TARIFF = "create-tariff";
    public static final String UPDATE_TARIFF = "update-tariff";
    public static final String DELETE_TARIFF = "delete-tariff";

    @Bean
    public NewTopic createTariffTopic() {
        return TopicBuilder
                .name(CREATE_TARIFF)
                .partitions(1)
                .replicas(1).build();
    }

    @Bean
    public NewTopic updateTariffTopic() {
        return TopicBuilder
                .name(UPDATE_TARIFF)
                .partitions(1)
                .replicas(1).build();
    }

    @Bean
    public NewTopic deleteTariffTopic() {
        return TopicBuilder
                .name(DELETE_TARIFF)
                .partitions(1)
                .replicas(1).build();
    }
}
