package com.example.bookingapp.configuration.kafka;

import com.example.bookingapp.dto.kafkaEvent.BookingRoomEvent;
import com.example.bookingapp.dto.kafkaEvent.RegistrationUserEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Bean
    public ProducerFactory<String, RegistrationUserEvent> registrationUserEventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerFactoryConfig(), new StringSerializer(), new JsonSerializer<>());
    }


    @Bean
    public KafkaTemplate<String, RegistrationUserEvent> registrationUserEventKafkaTemplate() {
        return new KafkaTemplate<>(registrationUserEventProducerFactory());
    }

    @Bean
    public ProducerFactory<String, BookingRoomEvent> bookingRoomEventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerFactoryConfig(), new StringSerializer(), new JsonSerializer<>());
    }

    @Bean
    public KafkaTemplate<String, BookingRoomEvent> bookingRoomEventKafkaTemplate() {
        return new KafkaTemplate<>(bookingRoomEventProducerFactory());
    }

    private Map<String, Object> producerFactoryConfig() {
        Map<String, Object> producerFactoryConfig = new HashMap<>();
        producerFactoryConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        producerFactoryConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerFactoryConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return producerFactoryConfig;
    }


}
