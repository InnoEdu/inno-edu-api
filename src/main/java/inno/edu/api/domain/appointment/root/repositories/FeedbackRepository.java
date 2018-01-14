package inno.edu.api.domain.appointment.root.repositories;

import inno.edu.api.domain.appointment.root.models.Feedback;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface FeedbackRepository extends CrudRepository<Feedback, UUID> {
    List<Feedback> findByAppointmentId(UUID appointmentId);
}
