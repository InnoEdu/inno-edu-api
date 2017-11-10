package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.appointment.exceptions.AppointmentNotFoundException;
import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Component
public class UpdateAppointmentCommand {
    private final AppointmentRepository appointmentRepository;

    public UpdateAppointmentCommand(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment run(UUID id, Appointment availability) {
        Appointment currentAppointment = ofNullable(appointmentRepository.findOne(id))
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        currentAppointment.setId(id);
        currentAppointment.setFromDateTime(availability.getFromDateTime());
        currentAppointment.setToDateTime(availability.getToDateTime());

        return appointmentRepository.save(currentAppointment);
    }
}
