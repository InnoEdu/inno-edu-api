package inno.edu.api.domain.appointment.queries;

import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.models.AppointmentStatus;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Query
public class GetAppointmentsByMentorProfileIdQuery {
    private final AppointmentRepository appointmentRepository;

    public GetAppointmentsByMentorProfileIdQuery( AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> run(UUID profileId, AppointmentStatus status) {
        if (nonNull(status)) {
            return appointmentRepository.findByMentorProfileIdAndStatus(profileId, status);
        }
        return appointmentRepository.findByMentorProfileId(profileId);
    }
}
