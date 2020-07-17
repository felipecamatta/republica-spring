package com.dev.republica.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class MoradoresIsEmptyException extends RuntimeException {

    public MoradoresIsEmptyException() {
        super("Forneça os moradores envolvidos na finança");
    }

}
