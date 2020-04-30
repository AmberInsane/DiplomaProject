package com.tms.stankevich.exception;

import lombok.Getter;

@Getter
public class SessionDeleteException extends Exception {
    private int countOfTickets;
    public SessionDeleteException(int count) {
        super();
        this.countOfTickets = count;
    }
}
