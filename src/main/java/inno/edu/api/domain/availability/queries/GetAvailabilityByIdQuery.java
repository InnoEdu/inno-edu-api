package inno.edu.api.domain.availability.queries;

import inno.edu.api.domain.availability.exceptions.AvailabilityNotFoundException;
import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Query
public class GetAvailabilityByIdQuery {
    private final AvailabilityRepository availabilityRepository;

    public GetAvailabilityByIdQuery(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    public Availability run(UUID id) {
        return ofNullable(availabilityRepository.findOne(id))
                .orElseThrow(() -> new AvailabilityNotFoundException(id));
    }
}
