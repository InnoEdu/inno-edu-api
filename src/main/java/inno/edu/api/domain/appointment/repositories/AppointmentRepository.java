package inno.edu.api.domain.appointment.repositories;

import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.models.AppointmentStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends CrudRepository<Appointment, UUID> {
    List<Appointment> findByMentorProfileId(UUID mentorProfileId);
    List<Appointment> findByMentorProfileIdAndStatus(UUID mentorProfileId, AppointmentStatus status);

    List<Appointment> findByMenteeProfileId(UUID menteeProfileId);
    List<Appointment> findByMenteeProfileIdAndStatus(UUID menteeProfileId, AppointmentStatus status);

}
