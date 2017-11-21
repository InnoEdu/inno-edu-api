package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.appointment.commands.dtos.UpdateAppointmentRequest;
import inno.edu.api.domain.appointment.commands.mappers.UpdateAppointmentRequestMapper;
import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.queries.GetAppointmentByIdQuery;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class UpdateAppointmentCommand {
    private final UpdateAppointmentRequestMapper updateAppointmentRequestMapper;

    private final GetAppointmentByIdQuery getAppointmentByIdQuery;
    private final AppointmentRepository appointmentRepository;

    public UpdateAppointmentCommand(UpdateAppointmentRequestMapper updateAppointmentRequestMapper, GetAppointmentByIdQuery getAppointmentByIdQuery, AppointmentRepository appointmentRepository) {
        this.updateAppointmentRequestMapper = updateAppointmentRequestMapper;
        this.getAppointmentByIdQuery = getAppointmentByIdQuery;
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment run(UUID id, UpdateAppointmentRequest updateAppointmentRequest) {
        Appointment currentAppointment = getAppointmentByIdQuery.run(id);
        updateAppointmentRequestMapper.setAppointment(updateAppointmentRequest, currentAppointment);
        return appointmentRepository.save(currentAppointment);
    }
}
