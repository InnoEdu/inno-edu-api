package inno.edu.api.domain.profile.interest.commands;

import inno.edu.api.domain.profile.interest.commands.dtos.CreateInterestRequest;
import inno.edu.api.domain.profile.interest.commands.mappers.CreateInterestRequestMapper;
import inno.edu.api.domain.profile.interest.models.Interest;
import inno.edu.api.domain.profile.interest.repositories.InterestRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;

import java.util.UUID;

@Command
public class CreateInterestCommand {
    private final UUIDGeneratorService uuidGeneratorService;

    private final CreateInterestRequestMapper createInterestRequestMapper;
    private final ProfileExistsAssertion profileExistsAssertion;

    private final InterestRepository interestRepository;

    public CreateInterestCommand(UUIDGeneratorService uuidGeneratorService, CreateInterestRequestMapper createInterestRequestMapper, ProfileExistsAssertion profileExistsAssertion, InterestRepository interestRepository) {
        this.uuidGeneratorService = uuidGeneratorService;
        this.createInterestRequestMapper = createInterestRequestMapper;
        this.profileExistsAssertion = profileExistsAssertion;
        this.interestRepository = interestRepository;
    }

    public Interest run(UUID profileId, CreateInterestRequest createInterestRequest) {
        profileExistsAssertion.run(profileId);

        Interest interest = createInterestRequestMapper.toInterest(createInterestRequest);
        interest.setId(uuidGeneratorService.generate());
        interest.setProfileId(profileId);

        return interestRepository.save(interest);
    }
}
