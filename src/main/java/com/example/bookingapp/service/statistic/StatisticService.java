package com.example.bookingapp.service.statistic;

import com.example.bookingapp.dto.kafkaEvent.BookingRoomEvent;
import com.example.bookingapp.dto.kafkaEvent.RegistrationUserEvent;
import com.example.bookingapp.entity.mongodb.BookingRoomInformation;
import com.example.bookingapp.entity.mongodb.RegistrationUserInformation;
import com.example.bookingapp.mapper.StatisticMapper;
import com.example.bookingapp.repository.mongodb.BookingRoomRepository;
import com.example.bookingapp.repository.mongodb.RegistrationUserRepository;
import com.example.bookingapp.repository.mongodb.SequenceDAO;
import com.example.bookingapp.util.ZonedDateTimeToDateConverter;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatisticService {

    @Value("${app.statistic.registrationUserInformationPath}")
    private String registrationUserInformationPath;
    @Value("${app.statistic.bookingRoomInformationPath}")
    private String bookingRoomInformationPath;

    private final RegistrationUserRepository registrationUserRepository;
    private final BookingRoomRepository bookingRoomRepository;
    private final StatisticMapper statisticMapper;
    private final SequenceDAO sequenceDAO;
    private final ZonedDateTimeToDateConverter converter = new ZonedDateTimeToDateConverter();

    @KafkaListener(topics = "${app.kafka_topics.registration-user-topic}", groupId = "${spring.kafka.consumer.group-id}"
            , containerFactory = "registrationUserEventConcurrentKafkaListenerContainerFactory")
    public void registrationUserTopicListener(@Payload RegistrationUserEvent registrationUserEvent) {
        RegistrationUserInformation userInformation = statisticMapper.fromUserEventToRegistrationUserInf(registrationUserEvent);
        userInformation.setId(sequenceDAO.getNewSequence(RegistrationUserInformation.REGISTRATION_USER_SEQ_KEY));
        userInformation.setCreateAt(converter.convert(registrationUserEvent.getCreateAt()));
        registrationUserRepository.save(userInformation);

        downloadStatistic();

    }

    @KafkaListener(topics = "${app.kafka_topics.booking-room-topic}", groupId = "${spring.kafka.consumer.group-id}"
            , containerFactory = "bookingRoomEventConcurrentKafkaListenerContainerFactory")
    public void bookingRoomEventListener(@Payload BookingRoomEvent bookingRoomEvent) {
        BookingRoomInformation bookingRoomInformation = statisticMapper.fromRoomEventToBookingRoomInformation(bookingRoomEvent);
        bookingRoomInformation.setId(sequenceDAO.getNewSequence(BookingRoomInformation.BOOKING_ROOM_SEQ_KEY));
        bookingRoomInformation.setCreateAt(Date.from(Instant.now()));
        bookingRoomInformation.setArrivalDate(converter.convert(bookingRoomEvent.getArrivalDate()));
        bookingRoomInformation.setDepartureDate(converter.convert(bookingRoomEvent.getDepartureDate()));
        bookingRoomRepository.save(bookingRoomInformation);
    }

    @SneakyThrows
    public void downloadStatistic() {

        CompletableFuture<File> registrationUserCompletableFuture = CompletableFuture.supplyAsync(() ->



                createCsvFile(registrationUserRepository.findAll(), Path.of(registrationUserInformationPath)));


        CompletableFuture<File> bookingRoomCompletableFuture = CompletableFuture.supplyAsync(() ->
                createCsvFile(bookingRoomRepository.findAll(), Path.of(bookingRoomInformationPath)));

        CompletableFuture.allOf(registrationUserCompletableFuture, bookingRoomCompletableFuture).get();


    }

    @SneakyThrows
    private File createCsvFile(List<?> objects, Path path) {
        List<String[]> lines = new ArrayList<>();

        objects.forEach(object -> {
            List<Field> fields = Arrays.asList(object.getClass().getDeclaredFields());

            String[] row = new String[fields.size()];

            for (int i = 0; i < fields.size(); i++) {
                row[i] = fields.get(i).toString();
            }

            lines.add(row);
        });

        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        CSVWriter writer = new CSVWriter(new FileWriter(path.toString()));

        writer.writeAll(lines);
        writer.close();

        return new File(path.toString());

    }


}
