package inno.edu.api.domain.availability.queries;

import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;
import java.util.UUID;

@Query
public class GetAvailabilityByProfileId {
    private final AvailabilityRepository availabilityRepository;

    public GetAvailabilityByProfileId(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    public List<Availability> run(UUID profileId) {
        return availabilityRepository.findByMentorProfileId(profileId);
    }
}
