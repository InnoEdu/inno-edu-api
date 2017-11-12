package inno.edu.api.domain.appointment.repositories;

import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.models.AppointmentStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends CrudRepository<Appointment, UUID> {
    List<Appointment> findByUniversityId(UUID universityId);
    List<Appointment> findByUniversityIdAndStatus(UUID universityId, AppointmentStatus status);

    List<Appointment> findByMentorId(UUID universityId);
    List<Appointment> findByMentorIdAndStatus(UUID universityId, AppointmentStatus status);

    List<Appointment> findByMenteeId(UUID universityId);
    List<Appointment> findByMenteeIdAndStatus(UUID universityId, AppointmentStatus status);

}
