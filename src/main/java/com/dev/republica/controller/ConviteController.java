package com.dev.republica.controller;

import com.dev.republica.dto.ConviteResponse;
import com.dev.republica.service.ConviteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ConviteController {

    private final ConviteService conviteService;

    @GetMapping("/republicas/{idRepublica}/convites")
    public ResponseEntity<List<ConviteResponse>> getByRepublica(@PathVariable Long idRepublica,
                                                                @RequestParam(defaultValue = "0") int pagina,
                                                                @RequestParam(defaultValue = "20") int itemsPorPagina) {
        return ResponseEntity.ok(conviteService.getByRepublica(idRepublica, pagina, itemsPorPagina));
    }

    @GetMapping("/moradores/{idMorador}/convites")
    public ResponseEntity<List<ConviteResponse>> getByMorador(@PathVariable Long idMorador,
                                                              @RequestParam(defaultValue = "0") int pagina,
                                                              @RequestParam(defaultValue = "20") int itemsPorPagina) {
        return ResponseEntity.ok(conviteService.getByMorador(idMorador, pagina, itemsPorPagina));
    }

    @PreAuthorize("hasRole('REPRESENTANTE')")
    @PostMapping("/republicas/{idRepublica}/convidar/{idConvidado}")
    public ResponseEntity<Void> create(@PathVariable Long idRepublica, @PathVariable Long idConvidado) {
        conviteService.create(idRepublica, idConvidado);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/convites/{id}/aceitar")
    public ResponseEntity<Void> aceitar(@PathVariable Long id) {
        conviteService.aceitar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/convites/{id}/rejeitar")
    public ResponseEntity<Void> rejeitar(@PathVariable Long id) {
        conviteService.rejeitar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('REPRESENTANTE')")
    @DeleteMapping("/convites/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        conviteService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
