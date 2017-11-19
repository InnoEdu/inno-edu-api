package inno.edu.api.domain.availability.commands;

import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import inno.edu.api.domain.profile.assertions.MentorProfileExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;

import static java.util.UUID.randomUUID;

@Command
public class CreateAvailabilityCommand {
    private final MentorProfileExistsAssertion mentorProfileExistsAssertion;

    private final AvailabilityRepository availabilityRepository;

    public CreateAvailabilityCommand(MentorProfileExistsAssertion mentorProfileExistsAssertion, AvailabilityRepository availabilityRepository) {
        this.mentorProfileExistsAssertion = mentorProfileExistsAssertion;
        this.availabilityRepository = availabilityRepository;
    }

    public Availability run(Availability availability) {
        mentorProfileExistsAssertion.run(availability.getMentorProfileId());

        availability.setId(randomUUID());
        return availabilityRepository.save(availability);
    }
}
