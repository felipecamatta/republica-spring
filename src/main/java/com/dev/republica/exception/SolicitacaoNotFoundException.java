package com.dev.republica.exception;

public class SolicitacaoNotFoundException extends RuntimeException {

    public SolicitacaoNotFoundException(Long id) {
        super("Não foi possível encontrar solicitação com id " + id);
    }
}
