package com.dev.republica.controller;

import com.dev.republica.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ResourceAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConviteNotFoundException.class, FeedbackNotFoundException.class, FinancaNotFoundException.class, MoradorNotFoundException.class, RepublicaNotFoundException.class, SolicitacaoNotFoundException.class, TarefaNotFoundException.class})
    public void notFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler({MoradorHasRepublicaException.class, RepublicaFullException.class,
            RepublicaHasDespesaPendenteException.class, RepublicaNumeroDeVagasException.class,
            UsernameAlreadyExistException.class, MoradorIsRepresentanteException.class,
            MoradoresIsEmptyException.class})
    public void handleException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }

}
