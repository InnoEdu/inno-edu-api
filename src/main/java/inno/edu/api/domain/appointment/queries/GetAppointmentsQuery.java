package inno.edu.api.domain.appointment.queries;

import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;
import java.util.UUID;

import static com.google.common.collect.Lists.newArrayList;

@Query
public class GetAppointmentsQuery {
    private final AppointmentRepository appointmentRepository;

    public GetAppointmentsQuery(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> run(UUID id) {
        return newArrayList(appointmentRepository.findAll());
    }
}
