package com.tms.stankevich.exception;

import lombok.Getter;

@Getter
public class GenreDeleteException extends Exception {
    private int countOfMovies;
    public GenreDeleteException(int count) {
        super();
        this.countOfMovies = count;
    }
}
