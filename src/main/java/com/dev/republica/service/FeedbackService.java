package com.dev.republica.service;

import com.dev.republica.dto.FeedbackRequest;
import com.dev.republica.dto.FeedbackResponse;
import com.dev.republica.exception.FeedbackNotFoundException;
import com.dev.republica.exception.RepublicaNotFoundException;
import com.dev.republica.mapper.FeedbackMapper;
import com.dev.republica.model.Feedback;
import com.dev.republica.model.Morador;
import com.dev.republica.model.Republica;
import com.dev.republica.repository.FeedbackRepository;
import com.dev.republica.repository.MoradorRepository;
import com.dev.republica.repository.RepublicaRepository;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class FeedbackService {

    private final AuthService authService;
    private final FeedbackRepository feedbackRepository;
    private final RepublicaRepository republicaRepository;
    private final MoradorRepository moradorRepository;

    public FeedbackResponse getFeedback(Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new FeedbackNotFoundException(id));

        return FeedbackMapper.INSTANCE.feedbackToResponse(feedback);
    }

    public List<FeedbackResponse> getFeedbackByRepublica(Long idRepublica, String ordenarPor, int pagina, int itemPorPagina) {
        Republica republica = republicaRepository.findById(idRepublica)
                .orElseThrow(() -> new RepublicaNotFoundException(idRepublica));

        Page<Feedback> feedbacks = feedbackRepository.findByRepublica(republica, PageRequest.of(pagina, itemPorPagina, Sort.by(ordenarPor)));

        return FeedbackMapper.INSTANCE.feedbacksToResponse(feedbacks.getContent());
    }

    public void save(FeedbackRequest feedbackRequest, Long idRepublica) {
        Republica republica = republicaRepository.findById(idRepublica)
                .orElseThrow(() -> new RepublicaNotFoundException(idRepublica));

        Morador morador = authService.getCurrentUser().getMorador();

        Feedback feedback = FeedbackMapper.INSTANCE.toFeedback(feedbackRequest, republica, morador);

        List<Morador> moradores = moradorRepository.findAllById(feedbackRequest.getMoradoresIds());

        feedback.setMoradores(moradores);

        feedbackRepository.save(feedback);
    }

    public void delete(Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new FeedbackNotFoundException(id));

        feedback.setStatus("EXCLUIDO");

        feedbackRepository.save(feedback);
    }

    public void resolver(Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new FeedbackNotFoundException(id));

        if (feedback.getStatus().equals("RESOLVIDO-PENDENTE")) {
            feedback.setStatus("RESOLVIDO");
            int dias = Days.daysBetween(new DateTime(feedback.getDataFeedback()), new DateTime()).getDays();
            feedback.setIdade(dias);
        } else
            feedback.setStatus("RESOLVIDO-PENDENTE");

        feedbackRepository.save(feedback);
    }

}
