package com.dev.republica.exception;

public class FinancaNotFoundException extends RuntimeException {

    public FinancaNotFoundException(Long id) {
        super("Não foi possível encontrar financa com o id " + id);
    }

}
