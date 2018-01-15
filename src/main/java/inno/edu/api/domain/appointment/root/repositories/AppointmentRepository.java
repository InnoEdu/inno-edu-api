package inno.edu.api.domain.appointment.root.repositories;

import inno.edu.api.domain.appointment.root.models.Appointment;
import inno.edu.api.domain.appointment.root.models.AppointmentStatus;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends CrudRepository<Appointment, UUID>, QueryDslPredicateExecutor<Appointment> {
    List<Appointment> findByMentorProfileId(UUID mentorProfileId);
    List<Appointment> findByMentorProfileIdAndStatus(UUID mentorProfileId, AppointmentStatus status);

    List<Appointment> findByMenteeProfileId(UUID menteeProfileId);
    List<Appointment> findByMenteeProfileIdAndStatus(UUID menteeProfileId, AppointmentStatus status);

}
