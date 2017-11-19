package inno.edu.api.domain.availability.commands;

import inno.edu.api.domain.availability.assertions.AvailabilityExistsAssertion;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class DeleteAvailabilityCommand {
    private final AvailabilityExistsAssertion availabilityExistsAssertion;
    private final AvailabilityRepository availabilityRepository;

    public DeleteAvailabilityCommand(AvailabilityExistsAssertion availabilityExistsAssertion, AvailabilityRepository availabilityRepository) {
        this.availabilityExistsAssertion = availabilityExistsAssertion;
        this.availabilityRepository = availabilityRepository;
    }

    public void run(UUID id) {
        availabilityExistsAssertion.run(id);
        availabilityRepository.delete(id);
    }
}
