package inno.edu.api.domain.appointment.repositories;

import inno.edu.api.domain.appointment.models.Feedback;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FeedbackRepository extends CrudRepository<Feedback, UUID> {

}
