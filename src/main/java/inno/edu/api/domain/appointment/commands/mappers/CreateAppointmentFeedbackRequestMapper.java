package inno.edu.api.domain.appointment.commands.mappers;

import inno.edu.api.domain.appointment.commands.dtos.CreateAppointmentFeedbackRequest;
import inno.edu.api.domain.appointment.models.Feedback;
import org.mapstruct.Mapper;

@Mapper
public interface CreateAppointmentFeedbackRequestMapper {
    Feedback toFeedback(CreateAppointmentFeedbackRequest createAppointmentFeedbackRequest);
}
