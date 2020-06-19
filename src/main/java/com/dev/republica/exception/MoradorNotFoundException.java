package com.dev.republica.exception;

public class MoradorNotFoundException extends RuntimeException {

    public MoradorNotFoundException(Long id) {
        super("Não foi possível encontrar morado com o id " + id);
    }
    
}
