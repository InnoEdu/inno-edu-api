package inno.edu.api.domain.appointment.feedback.commands.mappers;

import inno.edu.api.domain.appointment.feedback.commands.dtos.CreateFeedbackRequest;
import inno.edu.api.domain.appointment.feedback.models.Feedback;
import org.mapstruct.Mapper;

@Mapper
public interface CreateFeedbackRequestMapper {
    Feedback toFeedback(CreateFeedbackRequest createFeedbackRequest);
}
