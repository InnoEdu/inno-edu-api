package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.appointment.exceptions.AppointmentNotFoundException;
import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.queries.GetAppointmentByIdQuery;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Command
public class UpdateAppointmentCommand {
    private final GetAppointmentByIdQuery getAppointmentByIdQuery;
    private final AppointmentRepository appointmentRepository;

    public UpdateAppointmentCommand(GetAppointmentByIdQuery getAppointmentByIdQuery, AppointmentRepository appointmentRepository) {
        this.getAppointmentByIdQuery = getAppointmentByIdQuery;
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment run(UUID id, Appointment appointment) {
        Appointment currentAppointment = getAppointmentByIdQuery.run(id);

        currentAppointment.setFromDateTime(appointment.getFromDateTime());
        currentAppointment.setToDateTime(appointment.getToDateTime());
        currentAppointment.setDescription(appointment.getDescription());
        currentAppointment.setStatus(appointment.getStatus());

        return appointmentRepository.save(currentAppointment);
    }
}
