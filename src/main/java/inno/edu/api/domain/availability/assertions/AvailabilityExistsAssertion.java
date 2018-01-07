package inno.edu.api.domain.availability.assertions;

import inno.edu.api.domain.availability.exceptions.AvailabilityNotFoundException;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import inno.edu.api.domain.common.assertions.EntityExistsAssertion;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;
import java.util.function.Function;

@Assertion
public class AvailabilityExistsAssertion extends EntityExistsAssertion<AvailabilityRepository, AvailabilityNotFoundException, Function<UUID, AvailabilityNotFoundException>> {
    protected AvailabilityExistsAssertion(AvailabilityRepository repository) {
        super(repository, AvailabilityNotFoundException::new);
    }
}