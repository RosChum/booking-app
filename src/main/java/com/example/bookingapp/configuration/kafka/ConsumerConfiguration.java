package com.example.bookingapp.configuration.kafka;

import com.example.bookingapp.dto.kafkaEvent.BookingRoomEvent;
import com.example.bookingapp.dto.kafkaEvent.RegistrationUserEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConsumerConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, BookingRoomEvent> bookingRoomEventConsumerFactoryconsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerFactoryConfiguration(), new StringDeserializer(), new JsonDeserializer<>());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BookingRoomEvent> bookingRoomEventConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, BookingRoomEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(bookingRoomEventConsumerFactoryconsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, RegistrationUserEvent> registrationUserEventConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerFactoryConfiguration(), new StringDeserializer(), new JsonDeserializer<>());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, RegistrationUserEvent> registrationUserEventConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, RegistrationUserEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(registrationUserEventConsumerFactory());
        return factory;
    }

    private Map<String, Object> consumerFactoryConfiguration() {
        Map<String, Object> factoryConfiguration = new HashMap<>();
        factoryConfiguration.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        factoryConfiguration.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        factoryConfiguration.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        factoryConfiguration.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        factoryConfiguration.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return factoryConfiguration;
    }
}
