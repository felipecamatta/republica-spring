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
    public ResponseEntity<List<SolicitacaoResponse>> getByRepublica(@PathVariable Long idRepublica) {
        return ResponseEntity.ok(solicitacaoService.getByRepublica(idRepublica));
    }

    @GetMapping("moradores/{idMorador}/solicitacoes")
    public ResponseEntity<List<SolicitacaoResponse>> getByMorador(@PathVariable Long idMorador) {
        return ResponseEntity.ok(solicitacaoService.getByMorador(idMorador));
    }

    @PostMapping("/moradores/{idMorador}/solicitar/{idRepublica}")
    public ResponseEntity<Void> create(@PathVariable Long idMorador, @PathVariable Long idRepublica) {
        solicitacaoService.create(idRepublica, idMorador);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('REPRESENTANTE')")
    @PostMapping("/republicas/{idRepublica}/solicitacoes/{id}")
    public ResponseEntity<String> aceitar(@PathVariable Long id) {
        return ResponseEntity.ok(solicitacaoService.aceitar(id));
    }

    @PreAuthorize("hasRole('REPRESENTANTE')")
    @DeleteMapping("/republicas/{idRepublica}/solicitacoes/{id}")
    public ResponseEntity<Void> rejeitar(@PathVariable Long id) {
        solicitacaoService.rejeitar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("solicitacoes/{idSolicitacao}")
    public ResponseEntity<Void> delete(@PathVariable Long idSolicitacao) {
        solicitacaoService.delete(idSolicitacao);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
