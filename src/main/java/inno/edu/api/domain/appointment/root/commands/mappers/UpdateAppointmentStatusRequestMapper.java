package inno.edu.api.domain.appointment.root.commands.mappers;

import inno.edu.api.domain.appointment.root.commands.dtos.UpdateAppointmentStatusRequest;
import inno.edu.api.domain.appointment.root.models.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UpdateAppointmentStatusRequestMapper {
    void setAppointment(UpdateAppointmentStatusRequest updateAppointmentStatusRequest, @MappingTarget Appointment appointment);
}
