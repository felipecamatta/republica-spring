package com.dev.republica.exception;

public class ConviteNotFoundException extends RuntimeException {

    public ConviteNotFoundException(Long id) {
        super("Não foi possível encontrar o convite com id " + id);
    }

}
