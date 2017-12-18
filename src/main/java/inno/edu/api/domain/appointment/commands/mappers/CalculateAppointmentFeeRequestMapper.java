package inno.edu.api.domain.appointment.commands.mappers;

import inno.edu.api.domain.appointment.commands.dtos.CalculateAppointmentFeeRequest;
import inno.edu.api.domain.appointment.models.Appointment;
import org.mapstruct.Mapper;

@Mapper
public interface CalculateAppointmentFeeRequestMapper {
    CalculateAppointmentFeeRequest toAppointmentFeeRequest(Appointment createAppointmentRequest);
}
