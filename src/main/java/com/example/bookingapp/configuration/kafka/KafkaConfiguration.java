package com.example.bookingapp.configuration.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Value("${app.kafka_topics.registration-user-topic}")
    private String registrationUserTopic;

    @Value("${app.kafka_topics.booking-room-topic}")
    private String bookingRoomTopic;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> adminProperties = new HashMap<>();
        adminProperties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        return new KafkaAdmin(adminProperties);
    }


    @Bean
    NewTopic registrationUserTopic() {
        return new NewTopic(registrationUserTopic, 1, (short) 1);
    }

    @Bean
    NewTopic bookingRoomTopic() {
        return new NewTopic(bookingRoomTopic, 1, (short) 1);
    }

}
