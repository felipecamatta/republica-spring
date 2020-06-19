package com.dev.republica.service;

import com.dev.republica.dto.SolicitacaoResponse;
import com.dev.republica.exception.MoradorNotFoundException;
import com.dev.republica.exception.RepublicaNotFoundException;
import com.dev.republica.exception.SolicitacaoNotFoundException;
import com.dev.republica.mapper.SolicitacaoMapper;
import com.dev.republica.model.Morador;
import com.dev.republica.model.Republica;
import com.dev.republica.model.Solicitacao;
import com.dev.republica.repository.MoradorRepository;
import com.dev.republica.repository.RepublicaRepository;
import com.dev.republica.repository.SolicitacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;
    private final MoradorRepository moradorRepository;
    private final RepublicaRepository republicaRepository;

    public List<SolicitacaoResponse> getByMorador(Long idMorador) {
        Morador morador = moradorRepository.findById(idMorador)
                .orElseThrow(() -> new MoradorNotFoundException(idMorador));

        return SolicitacaoMapper.INSTANCE.solicitacoesToResponse(solicitacaoRepository.findBySolicitante(morador));
    }

    public List<SolicitacaoResponse> getByRepublica(Long idRepublica) {
        Republica republica = republicaRepository.findById(idRepublica)
                .orElseThrow(() -> new RepublicaNotFoundException(idRepublica));

        return SolicitacaoMapper.INSTANCE.solicitacoesToResponse(solicitacaoRepository.findByRepublica(republica));
    }

    public void create(Long idRepublica, Long idMorador) {
        Republica republica = republicaRepository.findById(idRepublica)
                .orElseThrow(() -> new RepublicaNotFoundException(idRepublica));

        Morador morador = moradorRepository.findById(idMorador)
                .orElseThrow(() -> new MoradorNotFoundException(idMorador));

        solicitacaoRepository.save(new Solicitacao(null, morador, republica, "PENDENTE"));
    }

    public String aceitar(Long id) {
        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new SolicitacaoNotFoundException(id));

        Republica republica = solicitacao.getRepublica();

        Morador morador = solicitacao.getSolicitante();

        if (morador.getRepublica() != null)
            return "Você já reside em uma república. Saia da mesma antes de aceitar o convite";

        boolean add = republica.adicionarMorador(morador);

        if (add) {
            solicitacao.setStatus("ACEITO");

            solicitacaoRepository.save(solicitacao);

            return "Convite aceito.";
        } else
            return "República cheia";
    }

    public void rejeitar(Long id) {
        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new SolicitacaoNotFoundException(id));

        solicitacao.setStatus("REJEITADO");

        solicitacaoRepository.save(solicitacao);
    }

    public void delete(Long id) {
        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new SolicitacaoNotFoundException(id));

        solicitacaoRepository.delete(solicitacao);
    }

}