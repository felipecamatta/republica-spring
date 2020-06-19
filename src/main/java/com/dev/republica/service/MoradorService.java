package com.dev.republica.service;

import com.dev.republica.dto.MoradorRequest;
import com.dev.republica.dto.MoradorResponse;
import com.dev.republica.exception.MoradorNotFoundException;
import com.dev.republica.mapper.MoradorMapper;
import com.dev.republica.model.Morador;
import com.dev.republica.repository.MoradorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class MoradorService {

    private final AuthService authService;
    private final MoradorRepository moradorRepository;

    @Transactional(readOnly = true)
    public List<MoradorResponse> getAllMoradores(String nome) {
        return MoradorMapper.INSTANCE.moradoresToResponse(moradorRepository.findByNomeContaining(nome));
    }

    @Transactional(readOnly = true)
    public MoradorResponse getMorador(Long id) {
        Morador morador = moradorRepository.findById(id)
                .orElseThrow(() -> new MoradorNotFoundException(id));
        return MoradorMapper.INSTANCE.moradorToResponse(morador);
    }

    public void save(MoradorRequest moradorRequest) {
        moradorRepository.save(MoradorMapper.INSTANCE.toMorador(moradorRequest, authService.getCurrentUser()));
    }

    public MoradorResponse update(Long id, MoradorRequest moradorRequest) {
        Morador morador = moradorRepository.findById(id)
                .orElseThrow(() -> new MoradorNotFoundException(id));

        MoradorMapper.INSTANCE.updateMoradorFromRequest(moradorRequest, morador);

        moradorRepository.save(morador);

        return MoradorMapper.INSTANCE.moradorToResponse(morador);
    }

}
