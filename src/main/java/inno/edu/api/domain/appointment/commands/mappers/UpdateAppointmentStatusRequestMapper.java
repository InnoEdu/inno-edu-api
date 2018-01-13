package inno.edu.api.domain.appointment.commands.mappers;

import inno.edu.api.domain.appointment.commands.dtos.UpdateAppointmentStatusRequest;
import inno.edu.api.domain.appointment.models.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UpdateAppointmentStatusRequestMapper {
    void setAppointment(UpdateAppointmentStatusRequest updateAppointmentStatusRequest, @MappingTarget Appointment appointment);
}
