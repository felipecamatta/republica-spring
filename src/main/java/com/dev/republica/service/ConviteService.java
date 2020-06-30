package com.dev.republica.service;

import com.dev.republica.dto.ConviteResponse;
import com.dev.republica.exception.*;
import com.dev.republica.mapper.ConviteMapper;
import com.dev.republica.model.*;
import com.dev.republica.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ConviteService {

    private final RoleService roleService;
    private final HistoricoMoradorRepository historicoMoradorRepository;
    private final ConviteRepository conviteRepository;
    private final MoradorRepository moradorRepository;
    private final RepublicaRepository republicaRepository;
    private final UserRepository userRepository;

    public List<ConviteResponse> getByMorador(Long idMorador) {
        Morador morador = moradorRepository.findById(idMorador)
                .orElseThrow(() -> new MoradorNotFoundException(idMorador));

        return ConviteMapper.INSTANCE.convitesToResponse(conviteRepository.findByConvidado(morador));
    }

    public List<ConviteResponse> getByRepublica(Long idRepublica) {
        Republica republica = republicaRepository.findById(idRepublica)
                .orElseThrow(() -> new MoradorNotFoundException(idRepublica));

        return ConviteMapper.INSTANCE.convitesToResponse(conviteRepository.findByRepublica(republica));
    }

    public void create(Long idRepublica, Long idMorador) {
        Republica republica = republicaRepository.findById(idRepublica)
                .orElseThrow(() -> new RepublicaNotFoundException(idRepublica));

        Morador morador = moradorRepository.findById(idMorador)
                .orElseThrow(() -> new MoradorNotFoundException(idMorador));

        conviteRepository.save(new Convite(null, republica, morador, "PENDENTE"));
    }

    public void aceitar(Long id) {
        Convite convite = conviteRepository.findById(id)
                .orElseThrow(() -> new ConviteNotFoundException(id));

        Republica republica = convite.getRepublica();

        Morador morador = convite.getConvidado();

        if (morador.getRepublica() != null)
            throw new MoradorHasRepublicaException();

        boolean add = republica.adicionarMorador(morador);

        if (add) {
            convite.setStatus("ACEITO");
            conviteRepository.save(convite);

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
        Convite convite = conviteRepository.findById(id)
                .orElseThrow(() -> new ConviteNotFoundException(id));

        convite.setStatus("REJEITADO");

        conviteRepository.save(convite);
    }

    public void delete(Long id) {
        Convite convite = conviteRepository.findById(id)
                .orElseThrow(() -> new ConviteNotFoundException(id));

        conviteRepository.delete(convite);
    }

}
