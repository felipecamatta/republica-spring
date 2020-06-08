package com.dev.republica.controller;

import com.dev.republica.dto.HistoricoRepresentanteResponse;
import com.dev.republica.exception.RepublicaNotFoundException;
import com.dev.republica.mapper.HistoricoRepresentanteMapper;
import com.dev.republica.model.Republica;
import com.dev.republica.repository.HistoricoRepresentanteRepository;
import com.dev.republica.repository.RepublicaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class HistoricoRepresentanteController {

    private final HistoricoRepresentanteRepository historicoRepresentanteRepository;
    private final RepublicaRepository republicaRepository;

    @GetMapping("/republicas/{id}/historico-representante")
    public ResponseEntity<List<HistoricoRepresentanteResponse>> getHistoricoRepresentanteByRepublica(@PathVariable Long id) {
        Republica republica = republicaRepository.findById(id)
                .orElseThrow(() -> new RepublicaNotFoundException(id.toString()));

        return ResponseEntity.ok(HistoricoRepresentanteMapper.INSTANCE.historicoRepresentantesToResponse(historicoRepresentanteRepository.findByRepublica(republica)));
    }

}
