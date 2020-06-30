package com.dev.republica.controller;

import com.dev.republica.dto.MoradorTarefaResolver;
import com.dev.republica.dto.TarefaRequest;
import com.dev.republica.dto.TarefaResponse;
import com.dev.republica.service.TarefaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class TarefaController {

    private final TarefaService tarefaService;

    @GetMapping("/tarefas/{id}")
    public ResponseEntity<TarefaResponse> getTarefa(@PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.getTarefa(id));
    }

    @GetMapping("/republicas/{idRepublica}/tarefas")
    public ResponseEntity<List<TarefaResponse>> getTarefaByRepublica(@PathVariable Long idRepublica) {
        return ResponseEntity.ok(tarefaService.getTarefaByRepublica(idRepublica));
    }

    @GetMapping("/republicas/{idRepublica}/morador/{idMorador}/tarefas")
    public ResponseEntity<List<TarefaResponse>> getTarefaByRepublicaAndMorador(@PathVariable Long idRepublica, @PathVariable Long idMorador) {
        return ResponseEntity.ok(tarefaService.getTarefaByRepublicaAndMorador(idRepublica, idMorador));
    }

    @PreAuthorize("hasRole('REPRESENTANTE')")
    @PostMapping("/republicas/{idRepublica}/tarefas")
    public ResponseEntity<Void> create(@RequestBody TarefaRequest tarefaRequest, @PathVariable Long idRepublica) {
        tarefaService.save(tarefaRequest, idRepublica);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('REPRESENTANTE')")
    @PutMapping("/tarefas/{id}")
    public ResponseEntity<TarefaResponse> update(@PathVariable Long id, @RequestBody TarefaRequest tarefaRequest) {
        return ResponseEntity.ok(tarefaService.update(id, tarefaRequest));
    }

    @PreAuthorize("hasRole('REPRESENTANTE')")
    @DeleteMapping("/tarefas/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tarefaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/republicas/{idRepublica}/morador/{idMorador}/tarefas/{idTarefa}/resolver")
    public ResponseEntity<Void> resolver(@PathVariable Long idRepublica, @PathVariable Long idMorador, @PathVariable Long idTarefa, @RequestBody MoradorTarefaResolver comentario) {
        tarefaService.resolver(idMorador, idTarefa, comentario);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
