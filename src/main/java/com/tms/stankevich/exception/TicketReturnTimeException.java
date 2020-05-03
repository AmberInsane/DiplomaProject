package com.tms.stankevich.exception;

public class TicketReturnTimeException extends Exception {
    public TicketReturnTimeException(String messageCode) {
        super(messageCode);
    }
}