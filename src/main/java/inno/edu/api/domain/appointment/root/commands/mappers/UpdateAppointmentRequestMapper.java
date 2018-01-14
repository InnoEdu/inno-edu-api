package inno.edu.api.domain.appointment.root.commands.mappers;

import inno.edu.api.domain.appointment.root.commands.dtos.UpdateAppointmentRequest;
import inno.edu.api.domain.appointment.root.models.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UpdateAppointmentRequestMapper {
    void setAppointment(UpdateAppointmentRequest updateAppointmentRequest, @MappingTarget Appointment appointment);
}
