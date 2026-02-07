package com.Sudoku.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidCharacterException extends RuntimeException{
    public InvalidCharacterException(String message) {
        super(message);
    }
}
