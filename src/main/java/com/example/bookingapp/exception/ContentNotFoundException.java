package com.example.bookingapp.exception;

public class ContentNotFoundException extends RuntimeException{
    public ContentNotFoundException(String message) {
        super(message);
    }
}
