package com.dev.republica.exception;

public class RepublicaNumeroDeVagasException extends RuntimeException {

    public RepublicaNumeroDeVagasException() {
        super("Número de vagas informado é menor que o número de moradores residentes na república");
    }

}
