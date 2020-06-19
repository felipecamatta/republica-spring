package com.dev.republica.exception;

public class FeedbackNotFoundException extends RuntimeException {

    public FeedbackNotFoundException(Long id) {
        super("Não foi possível encontrar o feedback com id " + id);
    }

}
