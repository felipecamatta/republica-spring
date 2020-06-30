package com.dev.republica.controller;

import com.dev.republica.dto.HistoricoMoradorResponse;
import com.dev.republica.exception.MoradorNotFoundException;
import com.dev.republica.mapper.HistoricoMoradorMapper;
import com.dev.republica.model.Morador;
import com.dev.republica.repository.HistoricoMoradorRepository;
import com.dev.republica.repository.MoradorRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class HistoricoMoradorController {

    private final MoradorRepository moradorRepository;
    private final HistoricoMoradorRepository historicoMoradorRepository;

    @GetMapping("/moradores/{idMorador}/historico")
    public ResponseEntity<List<HistoricoMoradorResponse>> getHistoricoMoradorByMorador(@PathVariable Long idMorador) {
        Morador morador = moradorRepository.findById(idMorador)
                .orElseThrow(() -> new MoradorNotFoundException(idMorador));

        return ResponseEntity.ok(HistoricoMoradorMapper.INSTANCE.historicoMoradoresToResponse(historicoMoradorRepository.findByMorador(morador)));
    }

}
