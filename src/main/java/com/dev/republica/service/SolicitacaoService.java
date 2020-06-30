package com.dev.republica.service;

import com.dev.republica.dto.SolicitacaoResponse;
import com.dev.republica.exception.*;
import com.dev.republica.mapper.SolicitacaoMapper;
import com.dev.republica.model.*;
import com.dev.republica.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class SolicitacaoService {

    private final RoleService roleService;
    private final HistoricoMoradorRepository historicoMoradorRepository;
    private final SolicitacaoRepository solicitacaoRepository;
    private final MoradorRepository moradorRepository;
    private final RepublicaRepository republicaRepository;
    private final UserRepository userRepository;

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

    public void aceitar(Long id) {
        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new SolicitacaoNotFoundException(id));

        Republica republica = solicitacao.getRepublica();

        Morador morador = solicitacao.getSolicitante();

        if (morador.getRepublica() != null)
            throw new MoradorHasRepublicaException();

        boolean add = republica.adicionarMorador(morador);

        if (add) {
            solicitacao.setStatus("ACEITO");
            solicitacaoRepository.save(solicitacao);

            moradorRepository.save(morador);
            republicaRepository.save(republica);

            User userMorador = userRepository.findById(morador.getId())
                    .orElseThrow();
            userMorador.addRole(roleService.getMoradorRole());
            userRepository.save(userMorador);

            historicoMoradorRepository.save(new HistoricoMorador(morador, republica));
        } else
            throw new RepublicaFullException();
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
