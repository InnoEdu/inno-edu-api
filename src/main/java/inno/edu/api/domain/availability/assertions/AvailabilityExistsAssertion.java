package inno.edu.api.domain.availability.assertions;

import inno.edu.api.domain.availability.exceptions.AvailabilityNotFoundException;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;

@Assertion
public class AvailabilityExistsAssertion {
    private final AvailabilityRepository availabilityRepository;

    public AvailabilityExistsAssertion(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    public void run(UUID id) {
        if (!availabilityRepository.exists(id)) {
                throw new AvailabilityNotFoundException(id);
        }
    }
}
