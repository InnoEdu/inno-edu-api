package inno.edu.api.domain.appointment.root.queries;

import inno.edu.api.domain.appointment.root.models.Appointment;
import inno.edu.api.domain.appointment.root.repositories.AppointmentRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Query
public class GetAppointmentsQuery {
    private final AppointmentRepository appointmentRepository;

    public GetAppointmentsQuery(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> run() {
        return newArrayList(appointmentRepository.findAll());
    }
}
