package com.dev.republica.service;

import com.dev.republica.dto.RepublicaRequest;
import com.dev.republica.dto.RepublicaResponse;
import com.dev.republica.exception.RepublicaNotFoundException;
import com.dev.republica.mapper.RepublicaMapper;
import com.dev.republica.model.Morador;
import com.dev.republica.model.Republica;
import com.dev.republica.repository.MoradorRepository;
import com.dev.republica.repository.RepublicaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class RepublicaService {

    private final AuthService authService;
    private final MoradorRepository moradorRepository;
    private final RepublicaRepository republicaRepository;

    @Transactional(readOnly = true)
    public List<RepublicaResponse> getRepublicasDisponiveis(String nome, String vantagens) {
        List<Republica> disponiveis = republicaRepository.findByNumeroVagasDisponiveisGreaterThanAndNomeContainingAndVantagensContaining(
                0, nome, vantagens);

        return RepublicaMapper.INSTANCE.republicasToResponse(disponiveis);
    }

    @Transactional(readOnly = true)
    public RepublicaResponse getRepublica(Long id) {
        Republica republica = republicaRepository.findById(id)
                .orElseThrow(() -> new RepublicaNotFoundException(id.toString()));

        return RepublicaMapper.INSTANCE.republicaToResponse(republica);
    }

    public void save(RepublicaRequest republicaRequest) {
        Morador representante = authService.getCurrentUser().getMorador();

        Republica republica = republicaRepository.save(RepublicaMapper.INSTANCE.toRepublica(republicaRequest, representante));

        representante.setRepublica(republica);

        representante.setRepresentante(true);

        moradorRepository.save(representante);
    }

    public RepublicaResponse update(Long id, RepublicaRequest republicaRequest) {
        Republica republica = republicaRepository.findById(id)
                .orElseThrow(() -> new RepublicaNotFoundException(id.toString()));

        RepublicaMapper.INSTANCE.updateRepublicaFromRequest(republicaRequest, republica);

        republicaRepository.save(republica);

        return RepublicaMapper.INSTANCE.republicaToResponse(republica);
    }

    public void delete(Long id) {
    }

}
