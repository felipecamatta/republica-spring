package com.dev.republica.controller;

import com.dev.republica.dto.SolicitacaoResponse;
import com.dev.republica.service.SolicitacaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;

    @GetMapping("/republicas/{idRepublica}/solicitacoes")
    public ResponseEntity<List<SolicitacaoResponse>> getByRepublica(@PathVariable Long idRepublica,
                                                                    @RequestParam(defaultValue = "0") int pagina,
                                                                    @RequestParam(defaultValue = "20") int itemsPorPagina) {
        return ResponseEntity.ok(solicitacaoService.getByRepublica(idRepublica, pagina, itemsPorPagina));
    }

    @GetMapping("/moradores/{idMorador}/solicitacoes")
    public ResponseEntity<List<SolicitacaoResponse>> getByMorador(@PathVariable Long idMorador,
                                                                  @RequestParam(defaultValue = "0") int pagina,
                                                                  @RequestParam(defaultValue = "20") int itemsPorPagina) {
        return ResponseEntity.ok(solicitacaoService.getByMorador(idMorador, pagina, itemsPorPagina));
    }

    @PostMapping("/moradores/{idMorador}/solicitar/{idRepublica}")
    public ResponseEntity<Void> create(@PathVariable Long idMorador, @PathVariable Long idRepublica) {
        solicitacaoService.create(idRepublica, idMorador);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('REPRESENTANTE')")
    @PostMapping("/solicitacoes/{id}/aceitar")
    public ResponseEntity<Void> aceitar(@PathVariable Long id) {
        solicitacaoService.aceitar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('REPRESENTANTE')")
    @DeleteMapping("/solicitacoes/{id}/rejeitar")
    public ResponseEntity<Void> rejeitar(@PathVariable Long id) {
        solicitacaoService.rejeitar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/solicitacoes/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        solicitacaoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
