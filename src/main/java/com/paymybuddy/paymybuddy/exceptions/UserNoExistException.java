package com.paymybuddy.paymybuddy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNoExistException extends RuntimeException {
    public UserNoExistException(String s) {
        super(s);
    }
}
