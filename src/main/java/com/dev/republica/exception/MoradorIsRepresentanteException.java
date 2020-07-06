package com.dev.republica.exception;

public class MoradorIsRepresentanteException extends RuntimeException {

    public MoradorIsRepresentanteException() {
        super("Não é possível remover esse morador pois ele é o representante da república");
    }

}
