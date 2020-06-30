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
    public ResponseEntity<List<ConviteResponse>> getByRepublica(@PathVariable Long idRepublica) {
        return ResponseEntity.ok(conviteService.getByRepublica(idRepublica));
    }

    @GetMapping("/moradores/{idMorador}/convites")
    public ResponseEntity<List<ConviteResponse>> getByMorador(@PathVariable Long idMorador) {
        return ResponseEntity.ok(conviteService.getByMorador(idMorador));
    }

    @PreAuthorize("hasRole('REPRESENTANTE')")
    @PostMapping("/republicas/{idRepublica}/convidar/{idConvidado}")
    public ResponseEntity<Void> create(@PathVariable Long idRepublica, @PathVariable Long idConvidado) {
        conviteService.create(idRepublica, idConvidado);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/convites/{id}/aceitar")
    public ResponseEntity<String> aceitar(@PathVariable Long id) {
        return ResponseEntity.ok(conviteService.aceitar(id));
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
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
