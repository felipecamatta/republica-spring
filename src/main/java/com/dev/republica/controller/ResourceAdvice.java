package com.dev.republica.controller;

import com.dev.republica.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
public class ResourceAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConviteNotFoundException.class, FeedbackNotFoundException.class, FinancaNotFoundException.class, MoradorNotFoundException.class, RepublicaNotFoundException.class, SolicitacaoNotFoundException.class, TarefaNotFoundException.class})
    public void notFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler({MoradorHasRepublicaException.class, RepublicaFullException.class, RepublicaHasDespesaPendenteException.class})
    public void handleException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value());
    }

}
