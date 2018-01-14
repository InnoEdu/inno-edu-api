package inno.edu.api.domain.appointment.root.commands.mappers;

import inno.edu.api.domain.appointment.root.commands.dtos.CreateAppointmentRequest;
import inno.edu.api.domain.appointment.root.models.Appointment;
import org.mapstruct.Mapper;

@Mapper
public interface CreateAppointmentRequestMapper {
    Appointment toAppointment(CreateAppointmentRequest createAppointmentRequest);
}
