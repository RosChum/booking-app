package com.example.bookingapp.controller;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        UserControllerTest.class,
        AuthenticationControllerTest.class,
        HotelControllerTest.class,
        RoomControllerTest.class,
        BookingControllerTest.class
})
public class SuiteTests {
}
