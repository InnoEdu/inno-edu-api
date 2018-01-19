package inno.edu.api.domain.appointment.feedback.respositories;

import inno.edu.api.domain.appointment.feedback.models.Feedback;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface FeedbackRepository extends CrudRepository<Feedback, UUID> {
    List<Feedback> findByAppointmentId(UUID appointmentId);


    @Query("SELECT f " +
            "FROM Feedback f " +
            "WHERE f.id = :profileId")
    List<Feedback> findByProfileId(UUID profileId);
}
