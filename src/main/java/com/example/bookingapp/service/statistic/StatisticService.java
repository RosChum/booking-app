package com.example.bookingapp.service.statistic;

import com.example.bookingapp.dto.kafkaEvent.BookingRoomEvent;
import com.example.bookingapp.dto.kafkaEvent.RegistrationUserEvent;
import com.example.bookingapp.entity.mongodb.BookingRoomInformation;
import com.example.bookingapp.entity.mongodb.RegistrationUserInformation;
import com.example.bookingapp.mapper.StatisticMapper;
import com.example.bookingapp.repository.mongodb.BookingRoomRepository;
import com.example.bookingapp.repository.mongodb.RegistrationUserRepository;
import com.example.bookingapp.repository.mongodb.SequenceDAO;
import com.example.bookingapp.util.CustomColumnPositionStrategy;
import com.example.bookingapp.util.ZonedDateTimeToDateConverter;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatisticService {

    @Value("${app.statistic.registrationUserInformationPath}")
    private String registrationUserInformationPath;
    @Value("${app.statistic.bookingRoomInformationPath}")
    private String bookingRoomInformationPath;
    @Value("${app.statistic.source}")
    private String sourceStatisticPath;


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
    public Resource downloadStatistic() {
        CompletableFuture<File> registrationUserCompletableFuture = CompletableFuture.supplyAsync(() ->
                createCsvFile(registrationUserRepository.findAll(), Path.of(registrationUserInformationPath)));

        CompletableFuture<File> bookingRoomCompletableFuture = CompletableFuture.supplyAsync(() ->
                createCsvFile(bookingRoomRepository.findAll(), Path.of(bookingRoomInformationPath)));

        CompletableFuture.allOf(registrationUserCompletableFuture, bookingRoomCompletableFuture).get();
        return createZipResponse();
    }

    @SneakyThrows
    private File createCsvFile(List<?> objects, Path path) {
        if (objects == null) {
            return null;
        }
        try (Writer writer = Files.newBufferedWriter(path)) {
            var strategyMapping = new CustomColumnPositionStrategy<>();
            strategyMapping.setType(objects.get(0).getClass());
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder<>(writer)
                    .withSeparator(';')
                    .withMappingStrategy(strategyMapping)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(objects);
        }
        return new File(path.toString());
    }

    @SneakyThrows
    private Resource createZipResponse() {
        File fileToZip = new File(sourceStatisticPath);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
        if (fileToZip.isDirectory()) {
            Arrays.stream(Objects.requireNonNull(fileToZip.listFiles())).forEach(file -> {
                if (file.isFile()) {
                    try (FileInputStream fileInputStream = new FileInputStream(file)) {
                        zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                            zipOutputStream.write(buffer, 0, bytesRead);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        zipOutputStream.closeEntry();
        zipOutputStream.close();
        return new ByteArrayResource(byteArrayOutputStream.toByteArray());
    }
}
