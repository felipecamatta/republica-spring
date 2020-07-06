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

import javax.validation.Valid;
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
    public ResponseEntity<List<TarefaResponse>> getTarefaByRepublica(@PathVariable Long idRepublica,
                                                                     @RequestParam(defaultValue = "dataTermino") String ordenarPor,
                                                                     @RequestParam(defaultValue = "0") int pagina,
                                                                     @RequestParam(defaultValue = "20") int itemsPorPagina) {
        return ResponseEntity.ok(tarefaService.getTarefaByRepublica(idRepublica, ordenarPor, pagina, itemsPorPagina));
    }

    @GetMapping("/republicas/{idRepublica}/morador/{idMorador}/tarefas")
    public ResponseEntity<List<TarefaResponse>> getTarefaByRepublicaAndMorador(@PathVariable Long idRepublica, @PathVariable Long idMorador,
                                                                               @RequestParam(defaultValue = "dataTermino") String ordenarPor,
                                                                               @RequestParam(defaultValue = "0") int pagina,
                                                                               @RequestParam(defaultValue = "20") int itemsPorPagina) {
        return ResponseEntity.ok(tarefaService.getTarefaByRepublicaAndMorador(idRepublica, idMorador, ordenarPor, pagina, itemsPorPagina));
    }

    @PreAuthorize("hasRole('REPRESENTANTE')")
    @PostMapping("/republicas/{idRepublica}/tarefas")
    public ResponseEntity<Void> create(@Valid @RequestBody TarefaRequest tarefaRequest, @PathVariable Long idRepublica) {
        tarefaService.save(tarefaRequest, idRepublica);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('REPRESENTANTE')")
    @PutMapping("/tarefas/{id}")
    public ResponseEntity<TarefaResponse> update(@PathVariable Long id, @Valid @RequestBody TarefaRequest tarefaRequest) {
        return ResponseEntity.ok(tarefaService.update(id, tarefaRequest));
    }

    @PreAuthorize("hasRole('REPRESENTANTE')")
    @DeleteMapping("/tarefas/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tarefaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/republicas/{idRepublica}/morador/{idMorador}/tarefas/{idTarefa}/resolver")
    public ResponseEntity<Void> resolver(@PathVariable Long idRepublica, @PathVariable Long idMorador, @PathVariable Long idTarefa, @RequestBody MoradorTarefaResolver comentario) {
        tarefaService.resolver(idMorador, idTarefa, comentario);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
