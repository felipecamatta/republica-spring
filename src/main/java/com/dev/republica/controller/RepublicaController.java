package com.dev.republica.controller;

import com.dev.republica.dto.RepublicaRequest;
import com.dev.republica.dto.RepublicaResponse;
import com.dev.republica.service.RepublicaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Void> create(@RequestBody RepublicaRequest republicaRequest) {
        republicaService.save(republicaRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RepublicaResponse> update(@PathVariable Long id, @RequestBody RepublicaRequest republicaRequest) {
        return ResponseEntity.ok().body(republicaService.update(id, republicaRequest));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        republicaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
