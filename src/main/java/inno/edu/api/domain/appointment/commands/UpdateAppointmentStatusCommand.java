package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.appointment.exceptions.AppointmentNotFoundException;
import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.models.AppointmentStatus;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Command
public class UpdateAppointmentStatusCommand {
    private final AppointmentRepository appointmentRepository;

    public UpdateAppointmentStatusCommand(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public void run(UUID appointmentId, AppointmentStatus status) {
        Appointment appointment = ofNullable(appointmentRepository.findOne(appointmentId))
                .orElseThrow(() -> new AppointmentNotFoundException(appointmentId));

        appointment.setStatus(status);
        appointmentRepository.save(appointment);
    }
}
