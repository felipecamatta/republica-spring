package com.dev.republica.controller;

import com.dev.republica.dto.DataChart;
import com.dev.republica.dto.FinancaRequest;
import com.dev.republica.dto.FinancaResponse;
import com.dev.republica.service.FinancaService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
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
    public ResponseEntity<List<FinancaResponse>> getFinancaByRepublica(@PathVariable Long idRepublica,
                                                                       @RequestParam(defaultValue = "") String tipo,
                                                                       @RequestParam(defaultValue = "") String descricao,
                                                                       @RequestParam(defaultValue = "#{new java.util.Date(0)}")
                                                                       @DateTimeFormat(pattern = "dd-MM-yyyy") Date dataInicio,
                                                                       @RequestParam(defaultValue = "#{new java.util.Date()}")
                                                                       @DateTimeFormat(pattern = "dd-MM-yyyy") Date dataFim,
                                                                       @RequestParam(required = false) Boolean efetivado,
                                                                       @RequestParam(defaultValue = "id") String ordenarPor,
                                                                       @RequestParam(defaultValue = "0") int pagina,
                                                                       @RequestParam(defaultValue = "20") int itemsPorPagina) {
        return ResponseEntity.ok(financaService.getFinancaByRepublica(idRepublica, tipo, descricao, dataInicio, dataFim, efetivado, ordenarPor, pagina, itemsPorPagina));
    }

    @GetMapping("/republicas/{idRepublica}/morador/{idMorador}/financas")
    public ResponseEntity<List<FinancaResponse>> getFinancaByRepublicaAndMorador(@PathVariable Long idRepublica,
                                                                                 @PathVariable Long idMorador,
                                                                                 @RequestParam(defaultValue = "") String tipo,
                                                                                 @RequestParam(defaultValue = "") String descricao,
                                                                                 @RequestParam(defaultValue = "#{new java.util.Date(0)}")
                                                                                 @DateTimeFormat(pattern = "dd-MM-yyyy") Date dataInicio,
                                                                                 @RequestParam(defaultValue = "#{new java.util.Date()}")
                                                                                 @DateTimeFormat(pattern = "dd-MM-yyyy") Date dataFim,
                                                                                 @RequestParam(defaultValue = "id") String ordenarPor,
                                                                                 @RequestParam(defaultValue = "0") int pagina,
                                                                                 @RequestParam(defaultValue = "20") int itemsPorPagina) {
        return ResponseEntity.ok(financaService.getFinancaByRepublicaAndMorador(idRepublica, idMorador, tipo, descricao, dataInicio, dataFim, ordenarPor, pagina, itemsPorPagina));
    }


    @PreAuthorize("hasRole('REPRESENTANTE')")
    @PostMapping("/republicas/{idRepublica}/financas")
    public ResponseEntity<Void> create(@Valid @RequestBody FinancaRequest financaRequest, @PathVariable Long idRepublica) {
        financaService.save(financaRequest, idRepublica);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('REPRESENTANTE')")
    @PutMapping("/financas/{id}")
    public ResponseEntity<FinancaResponse> update(@PathVariable Long id, @Valid @RequestBody FinancaRequest financaRequest) {
        return ResponseEntity.ok(financaService.update(id, financaRequest));
    }

    @PreAuthorize("hasRole('REPRESENTANTE')")
    @DeleteMapping("/financas/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        financaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
