package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.models.AppointmentReason;
import inno.edu.api.domain.appointment.models.AppointmentStatus;
import inno.edu.api.domain.appointment.queries.GetAppointmentByIdQuery;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class UpdateAppointmentStatusCommand {
    private final GetAppointmentByIdQuery getAppointmentByIdQuery;
    private final AppointmentRepository appointmentRepository;

    public UpdateAppointmentStatusCommand(GetAppointmentByIdQuery getAppointmentByIdQuery, AppointmentRepository appointmentRepository) {
        this.getAppointmentByIdQuery = getAppointmentByIdQuery;
        this.appointmentRepository = appointmentRepository;
    }

    public void run(UUID id, AppointmentReason reason, AppointmentStatus status) {
        Appointment appointment = getAppointmentByIdQuery.run(id);

        appointment.setStatus(status);
        appointment.setReason(reason.getReason());
        appointmentRepository.save(appointment);
    }
}
