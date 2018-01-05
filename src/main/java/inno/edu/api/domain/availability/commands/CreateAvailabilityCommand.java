package inno.edu.api.domain.availability.commands;

import inno.edu.api.domain.availability.commands.dtos.CreateAvailabilityRequest;
import inno.edu.api.domain.availability.commands.mappers.CreateAvailabilityRequestMapper;
import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;

@Command
public class CreateAvailabilityCommand {
    private final UUIDGeneratorService uuidGeneratorService;

    private final CreateAvailabilityRequestMapper createAvailabilityRequestMapper;
    private final ProfileExistsAssertion profileExistsAssertion;

    private final AvailabilityRepository availabilityRepository;

    public CreateAvailabilityCommand(UUIDGeneratorService uuidGeneratorService, CreateAvailabilityRequestMapper createAvailabilityRequestMapper, ProfileExistsAssertion profileExistsAssertion, AvailabilityRepository availabilityRepository) {
        this.uuidGeneratorService = uuidGeneratorService;
        this.createAvailabilityRequestMapper = createAvailabilityRequestMapper;
        this.profileExistsAssertion = profileExistsAssertion;
        this.availabilityRepository = availabilityRepository;
    }

    public Availability run(CreateAvailabilityRequest createAvailabilityRequest) {
        profileExistsAssertion.run(createAvailabilityRequest.getMentorProfileId());

        Availability availability = createAvailabilityRequestMapper.toAvailability(createAvailabilityRequest);
        availability.setId(uuidGeneratorService.generate());

        return availabilityRepository.save(availability);
    }
}
