package com.dev.republica.exception;

public class RepublicaHasDespesaPendenteException extends RuntimeException {

    public RepublicaHasDespesaPendenteException() {
        super("República possui despesas pendentes");
    }

}
