package inno.edu.api.domain.appointment.root.models.dtos.mappers;

import inno.edu.api.domain.appointment.root.models.dtos.CalculateAppointmentFeeRequest;
import inno.edu.api.domain.appointment.root.models.Appointment;
import org.mapstruct.Mapper;

@Mapper
public interface CalculateAppointmentFeeRequestMapper {
    CalculateAppointmentFeeRequest toAppointmentFeeRequest(Appointment createAppointmentRequest);
}
