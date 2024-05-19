package com.example.bookingapp.service.statistic;

import com.example.bookingapp.dto.kafkaEvent.BookingRoomEvent;
import com.example.bookingapp.dto.kafkaEvent.RegistrationUserEvent;
import com.example.bookingapp.entity.mongodb.BookingRoomInformation;
import com.example.bookingapp.entity.mongodb.RegistrationUserInformation;
import com.example.bookingapp.mapper.StatisticMapper;
import com.example.bookingapp.repository.mongodb.BookingRoomRepository;
import com.example.bookingapp.repository.mongodb.RegistrationUserRepository;
import com.example.bookingapp.repository.mongodb.SequenceDAO;
import com.example.bookingapp.repository.mongodb.SequenceRepository;
import com.example.bookingapp.util.ZonedDateTimeToDateConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatisticService {

    private final RegistrationUserRepository registrationUserRepository;
    private final BookingRoomRepository bookingRoomRepository;
    private final StatisticMapper statisticMapper;
    private final SequenceDAO sequenceDAO;
    private final SequenceRepository sequenceRepository;
    private final ZonedDateTimeToDateConverter converter = new ZonedDateTimeToDateConverter();

    @KafkaListener(topics = "${app.kafka_topics.registration-user-topic}", groupId = "${spring.kafka.consumer.group-id}"
            , containerFactory = "registrationUserEventConcurrentKafkaListenerContainerFactory")
    public void registrationUserTopicListener(@Payload RegistrationUserEvent registrationUserEvent) {
        RegistrationUserInformation userInformation = statisticMapper.fromUserEventToRegistrationUserInf(registrationUserEvent);
        userInformation.setId(sequenceDAO.getNewSequence(RegistrationUserInformation.REGISTRATION_USER_SEQ_KEY));
        userInformation.setCreateAt(converter.convert(registrationUserEvent.getCreateAt()));
        registrationUserRepository.save(userInformation);

        log.info("StatisticService  create registrationUser {} ", userInformation);

        log.info(" registrationUserRepository " + registrationUserRepository.findAll());
    }

    @KafkaListener(topics = "${app.kafka_topics.booking-room-topic}", groupId = "${spring.kafka.consumer.group-id}"
            , containerFactory = "bookingRoomEventConcurrentKafkaListenerContainerFactory")
    public void bookingRoomEventListener(@Payload BookingRoomEvent bookingRoomEvent) {
        BookingRoomInformation bookingRoomInformation = statisticMapper.fromRoomEventToBookingRoomInformation(bookingRoomEvent);
        bookingRoomInformation.setId(sequenceDAO.getNewSequence(BookingRoomInformation.BOOKING_ROOM_SEQ_KEY));
        bookingRoomInformation.setCreateAt(Date.from(Instant.now()));
        bookingRoomInformation.setArrivalDate(converter.convert(bookingRoomEvent.getArrivalDate()));
        bookingRoomInformation.setDepartureDate(converter.convert(bookingRoomEvent.getDepartureDate()));

        log.info(  " -----------------sequenceDAO BookingRoomInformation " + sequenceDAO.getS(BookingRoomInformation.BOOKING_ROOM_SEQ_KEY));

        bookingRoomRepository.save(bookingRoomInformation);

        log.info("StatisticService  create bookingRoom {} ", bookingRoomInformation);

        log.info(" bookingRoomEventListener " + bookingRoomRepository.findAll());

    }

}
