package com.tms.stankevich.exception;

public class MovieRateException extends Exception {
    public MovieRateException(String messageCode) {
        super(messageCode);
    }
}