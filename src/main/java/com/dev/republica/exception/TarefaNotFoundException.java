package com.dev.republica.exception;

public class TarefaNotFoundException extends RuntimeException {

    public TarefaNotFoundException(Long id) {
        super("Não foi possível encontrar tarefa com o id " + id);
    }

}
