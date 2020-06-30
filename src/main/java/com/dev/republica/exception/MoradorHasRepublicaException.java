package com.dev.republica.exception;

public class MoradorHasRepublicaException extends RuntimeException {

    public MoradorHasRepublicaException() {
        super("Você já reside em uma república. Saia da república antes de aceitar o convite.");
    }

}
