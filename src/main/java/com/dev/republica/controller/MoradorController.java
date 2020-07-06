package com.dev.republica.controller;

import com.dev.republica.dto.MoradorRequest;
import com.dev.republica.dto.MoradorResponse;
import com.dev.republica.service.MoradorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/moradores")
@AllArgsConstructor
public class MoradorController {

    private final MoradorService moradorService;

    @GetMapping
    public ResponseEntity<List<MoradorResponse>> getAllMoradores(@RequestParam(defaultValue = "") String nome,
                                                                 @RequestParam(defaultValue = "") String apelido,
                                                                 @RequestParam(defaultValue = "") String sexo,
                                                                 @RequestParam(defaultValue = "nome") String ordenarPor,
                                                                 @RequestParam(defaultValue = "0") int pagina,
                                                                 @RequestParam(defaultValue = "20") int itemsPorPagina) {
        return ResponseEntity.ok().body(moradorService.getAllMoradores(nome, apelido, sexo, ordenarPor, pagina, itemsPorPagina));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MoradorResponse> getMorador(@PathVariable Long id) {
        return ResponseEntity.ok().body(moradorService.getMorador(id));
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody MoradorRequest moradorRequest) {
        moradorService.save(moradorRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MoradorResponse> update(@PathVariable Long id, @Valid @RequestBody MoradorRequest moradorRequest) {
        return ResponseEntity.ok().body(moradorService.update(id, moradorRequest));
    }

}
