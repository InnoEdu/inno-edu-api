package inno.edu.api.domain.appointment.queries;

import inno.edu.api.domain.appointment.exceptions.AppointmentNotFoundException;
import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Query
public class GetAppointmentByIdQuery {
    private final AppointmentRepository appointmentRepository;

    public GetAppointmentByIdQuery(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment run(UUID id) {
        return ofNullable(appointmentRepository.findOne(id))
                .orElseThrow(() -> new AppointmentNotFoundException(id));
    }
}
