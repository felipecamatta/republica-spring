package com.dev.republica.controller;

import com.dev.republica.dto.RepublicaRequest;
import com.dev.republica.dto.RepublicaResponse;
import com.dev.republica.service.RepublicaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/republicas")
@AllArgsConstructor
public class RepublicaController {

    private final RepublicaService republicaService;

    @GetMapping
    public ResponseEntity<List<RepublicaResponse>> getRepublicasDisponiveis(@RequestParam(defaultValue = "") String nome, @RequestParam(defaultValue = "") String vantagens) {
        return ResponseEntity.ok().body(republicaService.getRepublicasDisponiveis(nome, vantagens));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepublicaResponse> getRepublica(@PathVariable Long id) {
        return ResponseEntity.ok().body(republicaService.getRepublica(id));
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody RepublicaRequest republicaRequest) {
        republicaService.save(republicaRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('REPRESENTANTE')")
    @PutMapping("/{id}")
    public ResponseEntity<RepublicaResponse> update(@PathVariable Long id, @Valid @RequestBody RepublicaRequest republicaRequest) {
        return republicaService.update(id, republicaRequest);
    }

    @PreAuthorize("hasRole('REPRESENTANTE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        republicaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('REPRESENTANTE')")
    @PostMapping("/{idRepublica}/alterarrepresentante/{idNovoRepresentante}")
    public ResponseEntity<Void> alterarRepresentante(@PathVariable Long idRepublica, @PathVariable Long idNovoRepresentante) {
        if (republicaService.alterarRepresentante(idRepublica, idNovoRepresentante)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

}
