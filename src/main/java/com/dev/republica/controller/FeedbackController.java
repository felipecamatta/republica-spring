package com.dev.republica.controller;

import com.dev.republica.dto.FeedbackRequest;
import com.dev.republica.dto.FeedbackResponse;
import com.dev.republica.service.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping("/feedbacks/{id}")
    public ResponseEntity<FeedbackResponse> getFeedback(@PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.getFeedback(id));
    }

    @GetMapping("/republicas/{idRepublica}/feedbacks")
    public ResponseEntity<List<FeedbackResponse>> getFeedbackByRepublica(@PathVariable Long idRepublica,
                                                                         @RequestParam(defaultValue = "dataFeedback") String ordenarPor,
                                                                         @RequestParam(defaultValue = "0") int pagina,
                                                                         @RequestParam(defaultValue = "20") int itemsPorPagina) {
        return ResponseEntity.ok(feedbackService.getFeedbackByRepublica(idRepublica, ordenarPor, pagina, itemsPorPagina));
    }

    @PostMapping("/republicas/{idRepublica}/feedbacks")
    public ResponseEntity<Void> create(@Valid @RequestBody FeedbackRequest feedbackRequest, @PathVariable Long idRepublica) {
        feedbackService.save(feedbackRequest, idRepublica);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/feedbacks/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        feedbackService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/feedbacks/{id}/resolver")
    public ResponseEntity<Void> resolver(@PathVariable Long id) {
        feedbackService.resolver(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
