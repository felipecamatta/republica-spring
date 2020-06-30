package com.dev.republica.controller;

import com.dev.republica.dto.DataChart;
import com.dev.republica.dto.FinancaRequest;
import com.dev.republica.dto.FinancaResponse;
import com.dev.republica.service.FinancaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class FinancaController {

    private final FinancaService financaService;

    @GetMapping("/financas/{id}")
    public ResponseEntity<FinancaResponse> getFinanca(@PathVariable Long id) {
        return ResponseEntity.ok(financaService.getFinanca(id));
    }

    @GetMapping("/republicas/{idRepublica}/financas")
    public ResponseEntity<List<FinancaResponse>> getFinancaByRepublica(@PathVariable Long idRepublica) {
        return ResponseEntity.ok(financaService.getFinancaByRepublica(idRepublica));
    }

    @GetMapping("/republicas/{idRepublica}/morador/{idMorador}/financas")
    public ResponseEntity<List<FinancaResponse>> getFinancaByRepublicaAndMorador(@PathVariable Long idRepublica, @PathVariable Long idMorador) {
        return ResponseEntity.ok(financaService.getFinancaByRepublicaAndMorador(idRepublica, idMorador));
    }

    @PreAuthorize("hasRole('REPRESENTANTE')")
    @PostMapping("/republicas/{idRepublica}/financas")
    public ResponseEntity<Void> create(@RequestBody FinancaRequest financaRequest, @PathVariable Long idRepublica) {
        financaService.save(financaRequest, idRepublica);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('REPRESENTANTE')")
    @PutMapping("/financas/{id}")
    public ResponseEntity<FinancaResponse> update(@PathVariable Long id, @RequestBody FinancaRequest financaRequest) {
        return ResponseEntity.ok(financaService.update(id, financaRequest));
    }

    @PreAuthorize("hasRole('REPRESENTANTE')")
    @DeleteMapping("/financas/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        financaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/republicas/{idRepublica}/morador/{idMorador}/financas/{idFinanca}/pagar")
    public ResponseEntity<Void> pagar(@PathVariable Long idRepublica, @PathVariable Long idMorador, @PathVariable Long idFinanca) {
        financaService.pagar(idMorador, idFinanca);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/republicas/{idRepublica}/financas/chart/{mes}/{ano}")
    public ResponseEntity<DataChart> chart(@PathVariable Long idRepublica, @PathVariable int mes, @PathVariable int ano) {
        return ResponseEntity.ok(financaService.getChart(idRepublica, mes, ano));
    }

}
