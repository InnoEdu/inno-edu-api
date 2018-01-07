package inno.edu.api.domain.availability.queries;

import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;
import java.util.UUID;

import static com.google.common.collect.Lists.newArrayList;

@Query
public class GetAvailabilityQuery {
    private final AvailabilityRepository availabilityRepository;

    public GetAvailabilityQuery(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    public List<Availability> run(UUID id) {
        return newArrayList(availabilityRepository.findAll());
    }
}
