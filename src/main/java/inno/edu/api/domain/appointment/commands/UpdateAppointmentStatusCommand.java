package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.appointment.assertions.AppointmentExistsAssertion;
import inno.edu.api.domain.appointment.commands.dtos.UpdateAppointmentStatusRequest;
import inno.edu.api.domain.appointment.commands.mappers.UpdateAppointmentStatusRequestMapper;
import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.queries.GetAppointmentByIdQuery;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class UpdateAppointmentStatusCommand {
    private final AppointmentExistsAssertion appointmentExistsAssertion;
    private final GetAppointmentByIdQuery getAppointmentByIdQuery;
    private final AppointmentRepository appointmentRepository;
    private final UpdateAppointmentStatusRequestMapper updateAppointmentStatusRequestMapper;

    public UpdateAppointmentStatusCommand(AppointmentExistsAssertion appointmentExistsAssertion, GetAppointmentByIdQuery getAppointmentByIdQuery, AppointmentRepository appointmentRepository, UpdateAppointmentStatusRequestMapper updateAppointmentStatusRequestMapper) {
        this.appointmentExistsAssertion = appointmentExistsAssertion;
        this.getAppointmentByIdQuery = getAppointmentByIdQuery;
        this.appointmentRepository = appointmentRepository;
        this.updateAppointmentStatusRequestMapper = updateAppointmentStatusRequestMapper;
    }

    public void run(UUID id, UpdateAppointmentStatusRequest updateAppointmentStatusRequest) {
        appointmentExistsAssertion.run(id);

        Appointment appointment = getAppointmentByIdQuery.run(id);
        updateAppointmentStatusRequestMapper.setAppointment(updateAppointmentStatusRequest, appointment);
        appointmentRepository.save(appointment);
    }
}
