package inno.edu.api.domain.appointment.queries;

import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.models.AppointmentStatus;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Component
public class GetAppointmentsByUniversityIdQuery {
    private final AppointmentRepository appointmentRepository;

    public GetAppointmentsByUniversityIdQuery(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> run(UUID universityId, AppointmentStatus status) {
        if (nonNull(status)) {
            return appointmentRepository.findAppointmentByUniversityIdAndStatus(universityId, status);
        }
        return appointmentRepository.findAppointmentByUniversityId(universityId);
    }
}
