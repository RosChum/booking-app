package com.example.bookingapp.service.statistic;

import com.example.bookingapp.dto.booking.BookingDto;
import com.example.bookingapp.dto.kafkaEvent.BookingRoomEvent;
import com.example.bookingapp.dto.kafkaEvent.RegistrationUserEvent;
import com.example.bookingapp.dto.user.UserDto;
import com.example.bookingapp.entity.mongodb.BookingRoomInformation;
import com.example.bookingapp.entity.mongodb.RegistrationUserInformation;
import com.example.bookingapp.mapper.StatisticMapper;
import com.example.bookingapp.repository.mongodb.BookingRoomRepository;
import com.example.bookingapp.repository.mongodb.RegistrationUserRepository;
import com.example.bookingapp.repository.mongodb.SequenceDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatisticService {

    private final RegistrationUserRepository registrationUserRepository;
    private final BookingRoomRepository bookingRoomRepository;
    private final StatisticMapper statisticMapper;
    private final SequenceDAO sequenceDAO;


    //TODO Caused by: org.bson.codecs.configuration.CodecConfigurationException: Can't find a codec for CodecCacheKey{clazz=class java.time.ZonedDateTime, types=null}.
    //необходимо конвертировать ZonedDateTime перед сохранением в монго

    @KafkaListener(topics = "${app.kafka_topics.registration-user-topic}", groupId = "${spring.kafka.consumer.group-id}"
            , containerFactory = "registrationUserEventConcurrentKafkaListenerContainerFactory")
    public void registrationUserTopicListener(@Payload RegistrationUserEvent registrationUserEvent) {
        RegistrationUserInformation userInformation = statisticMapper.fromUserEventToRegistrationUserInf(registrationUserEvent);
        userInformation.setId(sequenceDAO.getNewSequence(RegistrationUserInformation.REGISTRATION_USER_SEQ_KEY));
        registrationUserRepository.save(userInformation);

        log.info("StatisticService  create registrationUser {} ", userInformation);
    }

    @KafkaListener(topics = "${app.kafka_topics.booking-room-topic}", groupId = "${spring.kafka.consumer.group-id}"
            , containerFactory = "bookingRoomEventConcurrentKafkaListenerContainerFactory")
    private void bookingRoomEventListener(@Payload BookingRoomEvent bookingRoomEvent) {
        BookingRoomInformation bookingRoomInformation = statisticMapper.fromRoomEventToBookingRoomInformation(bookingRoomEvent);
        bookingRoomInformation.setId(sequenceDAO.getNewSequence(BookingRoomInformation.BOOKING_ROOM_SEQ_KEY));
        bookingRoomInformation.setCreateAt(ZonedDateTime.now());
        bookingRoomRepository.save(bookingRoomInformation);

        log.info("StatisticService  create bookingRoom {} ", bookingRoomInformation);
    }

}
