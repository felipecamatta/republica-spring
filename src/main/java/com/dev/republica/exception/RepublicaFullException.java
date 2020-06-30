package com.dev.republica.exception;

public class RepublicaFullException extends RuntimeException {

    public RepublicaFullException() {
        super("A república está cheia");
    }

}
