package com.tms.stankevich.exception;

import lombok.Getter;

@Getter
public class HallDeleteException extends Exception {
    private int countOfSessions;
    public HallDeleteException(int count) {
        super();
        this.countOfSessions = count;
    }
}
