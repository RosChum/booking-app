package com.example.bookingapp;

import com.example.bookingapp.repository.baserepository.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
//TODO положить все приложение в докер компос
// учетка админа по умолчанию     "password": "123456",
//    "email": "admin@mail.ru"

@EnableKafka
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.bookingapp.repository",repositoryBaseClass = BaseRepositoryImpl.class)
public class BookingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingAppApplication.class, args);
	}

}
