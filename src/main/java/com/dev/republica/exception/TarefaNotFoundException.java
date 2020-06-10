package com.dev.republica.exception;

public class TarefaNotFoundException extends RuntimeException {
    public TarefaNotFoundException(String message) {
        super(message);
    }
}
