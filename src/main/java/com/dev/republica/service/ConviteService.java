package com.dev.republica.service;

import com.dev.republica.dto.ConviteResponse;
import com.dev.republica.exception.ConviteNotFound;
import com.dev.republica.exception.MoradorNotFoundException;
import com.dev.republica.exception.RepublicaNotFoundException;
import com.dev.republica.mapper.ConviteMapper;
import com.dev.republica.model.Convite;
import com.dev.republica.model.Morador;
import com.dev.republica.model.Republica;
import com.dev.republica.repository.ConviteRepository;
import com.dev.republica.repository.MoradorRepository;
import com.dev.republica.repository.RepublicaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ConviteService {

    private final ConviteRepository conviteRepository;
    private final MoradorRepository moradorRepository;
    private final RepublicaRepository republicaRepository;

    public List<ConviteResponse> getByMorador(Long idMorador) {
        Morador morador = moradorRepository.findById(idMorador)
                .orElseThrow(() -> new MoradorNotFoundException(idMorador.toString()));

        return ConviteMapper.INSTANCE.convitesToResponse(conviteRepository.findByConvidado(morador));
    }

    public List<ConviteResponse> getByRepublica(Long idRepublica) {
        Republica republica = republicaRepository.findById(idRepublica)
                .orElseThrow(() -> new MoradorNotFoundException(idRepublica.toString()));

        return ConviteMapper.INSTANCE.convitesToResponse(conviteRepository.findByRepublica(republica));
    }

    public void create(Long idRepublica, Long idMorador) {
        Republica republica = republicaRepository.findById(idRepublica)
                .orElseThrow(() -> new RepublicaNotFoundException(idRepublica.toString()));

        Morador morador = moradorRepository.findById(idMorador)
                .orElseThrow(() -> new MoradorNotFoundException(idMorador.toString()));

        conviteRepository.save(new Convite(null, republica, morador, "PENDENTE"));
    }

    public String aceitar(Long id) {
        Convite convite = conviteRepository.findById(id)
                .orElseThrow(() -> new ConviteNotFound(id.toString()));

        Republica republica = convite.getRepublica();

        Morador morador = convite.getConvidado();

        if (morador.getRepublica() != null)
            return "Você já reside em uma república. Saia da mesma antes de aceitar o convite";

        boolean add = republica.adicionarMorador(morador);

        if (add) {
            convite.setStatus("ACEITO");

            conviteRepository.save(convite);

            return "Convite aceito.";
        } else
            return "República cheia";
    }

    public void rejeitar(Long id) {
        Convite convite = conviteRepository.findById(id)
                .orElseThrow(() -> new ConviteNotFound(id.toString()));

        convite.setStatus("REJEITADO");

        conviteRepository.save(convite);
    }

    public void delete(Long id) {
        Convite convite = conviteRepository.findById(id)
                .orElseThrow(() -> new ConviteNotFound(id.toString()));

        conviteRepository.delete(convite);
    }

}
