package com.dev.republica.exception;

public class UsernameAlreadyExistException extends RuntimeException {

    public UsernameAlreadyExistException() {
        super("Username já existe");
    }

}
