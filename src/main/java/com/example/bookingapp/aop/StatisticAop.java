package com.example.bookingapp.aop;

import com.example.bookingapp.dto.booking.BookingDto;
import com.example.bookingapp.dto.kafkaEvent.BookingRoomEvent;
import com.example.bookingapp.dto.kafkaEvent.RegistrationUserEvent;
import com.example.bookingapp.dto.user.UserDto;
import com.example.bookingapp.mapper.StatisticMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
public class StatisticAop {

    private final KafkaTemplate<String, RegistrationUserEvent> registrationUserEventKafkaTemplate;

    private final KafkaTemplate<String, BookingRoomEvent> bookingRoomEventKafkaTemplate;

    private final StatisticMapper statisticMapper;

    @Value("${app.kafka_topics.registration-user-topic}")
    private String registrationUserTopic;

    @Value("${app.kafka_topics.booking-room-topic}")
    private String bookingRoomTopic;

    @Pointcut("execution(* com.example.bookingapp.service.UserService.create*(..))")
    public void callingUserRegistration(){

    }

    @AfterReturning(value = "callingUserRegistration()", returning = "returnValue")
    public void afterReturningUserRegistration(JoinPoint joinPoint, Object returnValue) {

        if (returnValue instanceof UserDto) {
            UserDto userDto = (UserDto) returnValue;

    RegistrationUserEvent userEvent = statisticMapper.convertToRegistrationUserEvent(userDto);

    registrationUserEventKafkaTemplate.send(registrationUserTopic,userEvent);

        }



    }

}
