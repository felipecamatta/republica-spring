package com.dev.republica.mapper;

import com.dev.republica.dto.FeedbackRequest;
import com.dev.republica.dto.FeedbackResponse;
import com.dev.republica.model.Feedback;
import com.dev.republica.model.Morador;
import com.dev.republica.model.Republica;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;

@Mapper(imports = Date.class, uses = MoradorMapper.class)
public interface FeedbackMapper {

    FeedbackMapper INSTANCE = Mappers.getMapper(FeedbackMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(expression = "java(new Date())", target = "dataFeedback")
    @Mapping(source = "republica", target = "republica")
    @Mapping(source = "morador", target = "morador")
    @Mapping(target = "status", constant = "ABERTO")
    Feedback toFeedback(FeedbackRequest feedbackRequest, Republica republica, Morador morador);

    @Mapping(expression = "java(feedback.getRepublica().getNome())", target = "republicaNome")
    @Mapping(expression = "java(feedback.getMorador().getNome())", target = "moradorNome")
    @Mapping(source = "feedback.moradores", target = "moradores")
    FeedbackResponse feedbackToResponse(Feedback feedback);

    List<FeedbackResponse> feedbacksToResponse(List<Feedback> feedbacks);

}
