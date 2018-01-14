package inno.edu.api.domain.appointment.root.commands.mappers;

import inno.edu.api.domain.appointment.root.commands.dtos.CreateFeedbackRequest;
import inno.edu.api.domain.appointment.root.models.Feedback;
import org.mapstruct.Mapper;

@Mapper
public interface CreateFeedbackRequestMapper {
    Feedback toFeedback(CreateFeedbackRequest createFeedbackRequest);
}
