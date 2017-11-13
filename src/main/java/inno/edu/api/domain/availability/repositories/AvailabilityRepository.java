package inno.edu.api.domain.availability.repositories;

import inno.edu.api.domain.availability.models.Availability;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AvailabilityRepository extends CrudRepository<Availability, UUID> {
}