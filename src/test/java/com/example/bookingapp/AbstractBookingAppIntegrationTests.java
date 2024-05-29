package com.example.bookingapp;

import com.example.bookingapp.dto.booking.BookingDto;
import com.example.bookingapp.dto.hotel.HotelDto;
import com.example.bookingapp.dto.hotel.ShortHotelDto;
import com.example.bookingapp.dto.room.RoomDto;
import com.example.bookingapp.dto.room.RoomShortDto;
import com.example.bookingapp.dto.user.UserDto;
import com.example.bookingapp.dto.user.UserShortDto;
import com.example.bookingapp.entity.Role;
import com.example.bookingapp.entity.RoleType;
import com.example.bookingapp.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Testcontainers
@AutoConfigureMockMvc
public class AbstractBookingAppIntegrationTests {

    @Container
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:12.3")).withReuse(true);

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:6.0.8")).withReuse(true);

    @Container
    static RedisContainer redisContainer = new RedisContainer(DockerImageName.parse("redis:7.0.12")).withExposedPorts(6379).withReuse(true);


    @DynamicPropertySource
    public static void propertiesSource(DynamicPropertyRegistry registry) {
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.url", () -> jdbcUrl);

        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);

        registry.add("spring.data.redis.host", redisContainer::getHost);
        registry.add("spring.data.redis.port", () -> redisContainer.getMappedPort(6379).toString());


    }

    @Autowired
    protected BookingService bookingService;
    @Autowired
    protected HotelService hotelService;
    @Autowired
    protected RatingService ratingService;
    @Autowired
    protected RoomService roomService;
    @Autowired
    protected StatisticService statisticService;
    @Autowired
    protected UserService userService;

    @Autowired
    protected WebApplicationContext applicationContext;

    @Autowired
    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setUp() {
//        Role adminRole = new Role();
//        adminRole.setRoleType(RoleType.ROLE_ADMIN);
//        Role userRole = new Role();
//        adminRole.setRoleType(RoleType.ROLE_USER);
//
//        UserShortDto admin = new UserShortDto();
//        admin.setId(userService.create(new UserDto("Admin", "admin", "mail@mail.ru", ZonedDateTime.now(), null, Set.of(adminRole))).getId());
//        UserShortDto user = new UserShortDto();
//        user.setId(userService.create(new UserDto("User", "user", "mail1@mail.ru", ZonedDateTime.now(), null, Set.of(userRole))).getId());
//
//        HotelDto hotelDto = new HotelDto(ZonedDateTime.now(), ZonedDateTime.now(), "Hotel"
//                , "some description", "City", "street, 10"
//                , 10, null, null, null);
//        ShortHotelDto shortHotelDto = new ShortHotelDto();
//        shortHotelDto.setId(hotelService.create(hotelDto).getId());
//
//        RoomShortDto roomShortDto1 = new RoomShortDto();
//        roomShortDto1.setId(roomService.create(new RoomDto("room1", "some description"
//                , 101, new BigDecimal("115.50"), 3, shortHotelDto, null)).getId());
//
//        RoomShortDto roomShortDto2 = new RoomShortDto();
//        roomShortDto2.setId(roomService.create(new RoomDto("room2", "some description"
//                , 102, new BigDecimal("70.30"), 2, shortHotelDto, null)).getId());
//
//        bookingService.createBooking(new BookingDto(ZonedDateTime.parse("2024-01-01T10:00:00Z"), ZonedDateTime.parse("2024-01-10T10:00:00Z"), admin, roomShortDto1));
//        bookingService.createBooking(new BookingDto(ZonedDateTime.parse("2024-01-20T10:00:00Z"), ZonedDateTime.parse("2024-01-30T10:00:00Z"), admin, roomShortDto1));
    }


}
