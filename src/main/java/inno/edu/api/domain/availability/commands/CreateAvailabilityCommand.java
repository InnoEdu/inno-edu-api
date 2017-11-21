package inno.edu.api.domain.availability.commands;

import inno.edu.api.domain.availability.commands.dtos.CreateAvailabilityRequest;
import inno.edu.api.domain.availability.commands.mappers.CreateAvailabilityRequestMapper;
import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import inno.edu.api.domain.profile.assertions.MentorProfileExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;

import static java.util.UUID.randomUUID;

@Command
public class CreateAvailabilityCommand {
    private final CreateAvailabilityRequestMapper createAvailabilityRequestMapper;
    private final MentorProfileExistsAssertion mentorProfileExistsAssertion;

    private final AvailabilityRepository availabilityRepository;

    public CreateAvailabilityCommand(CreateAvailabilityRequestMapper createAvailabilityRequestMapper, MentorProfileExistsAssertion mentorProfileExistsAssertion, AvailabilityRepository availabilityRepository) {
        this.createAvailabilityRequestMapper = createAvailabilityRequestMapper;
        this.mentorProfileExistsAssertion = mentorProfileExistsAssertion;
        this.availabilityRepository = availabilityRepository;
    }

    public Availability run(CreateAvailabilityRequest createAvailabilityRequest) {
        mentorProfileExistsAssertion.run(createAvailabilityRequest.getMentorProfileId());

        Availability availability = createAvailabilityRequestMapper.toAvailability(createAvailabilityRequest);
        availability.setId(randomUUID());
        return availabilityRepository.save(availability);
    }
}
