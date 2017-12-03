package inno.edu.api.domain.availability.repositories;

import inno.edu.api.domain.availability.models.Availability;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface AvailabilityRepository extends CrudRepository<Availability, UUID> {
    List<Availability> findByMentorProfileId(UUID mentorProfileId);
}