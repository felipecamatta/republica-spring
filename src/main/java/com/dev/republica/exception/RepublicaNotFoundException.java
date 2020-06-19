package com.dev.republica.exception;

public class RepublicaNotFoundException extends RuntimeException {

    public RepublicaNotFoundException(Long id) {
        super("Não foi possível encontrar republica com o id " + id);
    }

}
