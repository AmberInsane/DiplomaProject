package com.tms.stankevich.exception;

import lombok.Getter;

@Getter
public class MovieDeleteException extends Exception {
    private int countOfSessions;
    public MovieDeleteException(int count) {
        super();
        this.countOfSessions = count;
    }
}
