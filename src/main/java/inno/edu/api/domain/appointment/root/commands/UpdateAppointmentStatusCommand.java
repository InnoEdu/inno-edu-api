package inno.edu.api.domain.appointment.root.commands;

import inno.edu.api.domain.appointment.root.assertions.AppointmentExistsAssertion;
import inno.edu.api.domain.appointment.root.commands.dtos.UpdateAppointmentStatusRequest;
import inno.edu.api.domain.appointment.root.commands.mappers.UpdateAppointmentStatusRequestMapper;
import inno.edu.api.domain.appointment.root.models.Appointment;
import inno.edu.api.domain.appointment.root.queries.GetAppointmentByIdQuery;
import inno.edu.api.domain.appointment.root.repositories.AppointmentRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

import static inno.edu.api.domain.appointment.root.models.AppointmentStatus.ACCEPTED;

@Command
public class UpdateAppointmentStatusCommand {
    private final AppointmentExistsAssertion appointmentExistsAssertion;
    private final GetAppointmentByIdQuery getAppointmentByIdQuery;
    private final AppointmentRepository appointmentRepository;
    private final UpdateAppointmentStatusRequestMapper updateAppointmentStatusRequestMapper;
    private final UpdateConflictingAppointmentsCommand updateConflictingAppointmentsCommand;

    public UpdateAppointmentStatusCommand(AppointmentExistsAssertion appointmentExistsAssertion, GetAppointmentByIdQuery getAppointmentByIdQuery, AppointmentRepository appointmentRepository, UpdateAppointmentStatusRequestMapper updateAppointmentStatusRequestMapper, UpdateConflictingAppointmentsCommand updateConflictingAppointmentsCommand) {
        this.appointmentExistsAssertion = appointmentExistsAssertion;
        this.getAppointmentByIdQuery = getAppointmentByIdQuery;
        this.appointmentRepository = appointmentRepository;
        this.updateAppointmentStatusRequestMapper = updateAppointmentStatusRequestMapper;
        this.updateConflictingAppointmentsCommand = updateConflictingAppointmentsCommand;
    }

    public void run(UUID id, UpdateAppointmentStatusRequest updateAppointmentStatusRequest) {
        appointmentExistsAssertion.run(id);

        Appointment appointment = getAppointmentByIdQuery.run(id);
        updateAppointmentStatusRequestMapper.setAppointment(updateAppointmentStatusRequest, appointment);
        appointmentRepository.save(appointment);

        if (updateAppointmentStatusRequest.getStatus() == ACCEPTED) {
            updateConflictingAppointmentsCommand.run(appointment);
        }
    }
}
