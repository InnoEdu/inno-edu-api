package inno.edu.api.domain.appointment.commands.mappers;

import inno.edu.api.domain.appointment.commands.dtos.CreateFeedbackRequest;
import inno.edu.api.domain.appointment.models.Feedback;
import org.mapstruct.Mapper;

@Mapper
public interface CreateFeedbackRequestMapper {
    Feedback toFeedback(CreateFeedbackRequest createFeedbackRequest);
}
