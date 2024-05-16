package com.example.bookingapp.aop;

import com.example.bookingapp.dto.booking.BookingDto;
import com.example.bookingapp.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
public class StatisticAop {

//    private final KafkaTemplate<String,UserDto> userDtoKafkaTemplate;
//
//    private final KafkaTemplate<String, BookingDto> bookingDtoKafkaTemplate;

    @Pointcut("execution(* com.example.bookingapp.service.UserService.create*(..))")
    public void callingUserRegistration(){

    }

    @AfterReturning(value = "callingUserRegistration()", returning = "returnValue")
    public void afterReturningUserRegistration(JoinPoint joinPoint, Object returnValue) {

        if (returnValue instanceof UserDto) {
            UserDto userDto = (UserDto) returnValue;

            log.info("StatisticAop {}", userDto);

        }



    }

}
