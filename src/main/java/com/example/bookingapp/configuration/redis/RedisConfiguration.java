package com.example.bookingapp.configuration.redis;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
//@EnableRedisRepositories(basePackages = "com.example.bookingapp.repository", basePackageClasses = RefreshTokenRepository.class)
public class RedisConfiguration {
    @Bean
    public JedisConnectionFactory connectionFactory(RedisProperties properties) {
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setPort(properties.getPort());
        standaloneConfiguration.setHostName(properties.getHost());
        return new JedisConnectionFactory(standaloneConfiguration);
    }


}
