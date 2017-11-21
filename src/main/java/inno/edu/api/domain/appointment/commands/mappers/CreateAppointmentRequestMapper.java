package inno.edu.api.domain.appointment.commands.mappers;

import inno.edu.api.domain.appointment.commands.dtos.CreateAppointmentRequest;
import inno.edu.api.domain.appointment.models.Appointment;
import org.mapstruct.Mapper;

@Mapper
public interface CreateAppointmentRequestMapper {
    Appointment toAppointment(CreateAppointmentRequest createAppointmentRequest);
}
