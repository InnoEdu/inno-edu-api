package inno.edu.api.domain.profile.accomplishment.commands;

import inno.edu.api.domain.profile.accomplishment.commands.dtos.CreateAccomplishmentRequest;
import inno.edu.api.domain.profile.accomplishment.commands.mappers.CreateAccomplishmentRequestMapper;
import inno.edu.api.domain.profile.accomplishment.models.Accomplishment;
import inno.edu.api.domain.profile.accomplishment.repositories.AccomplishmentRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;

import java.util.UUID;

@Command
public class CreateAccomplishmentCommand {
    private final UUIDGeneratorService uuidGeneratorService;

    private final CreateAccomplishmentRequestMapper createAccomplishmentRequestMapper;
    private final ProfileExistsAssertion profileExistsAssertion;

    private final AccomplishmentRepository accomplishmentRepository;

    public CreateAccomplishmentCommand(UUIDGeneratorService uuidGeneratorService, CreateAccomplishmentRequestMapper createAccomplishmentRequestMapper, ProfileExistsAssertion profileExistsAssertion, AccomplishmentRepository accomplishmentRepository) {
        this.uuidGeneratorService = uuidGeneratorService;
        this.createAccomplishmentRequestMapper = createAccomplishmentRequestMapper;
        this.profileExistsAssertion = profileExistsAssertion;
        this.accomplishmentRepository = accomplishmentRepository;
    }

    public Accomplishment run(UUID profileId, CreateAccomplishmentRequest createAccomplishmentRequest) {
        profileExistsAssertion.run(profileId);

        Accomplishment accomplishment = createAccomplishmentRequestMapper.toAccomplishment(createAccomplishmentRequest);
        accomplishment.setId(uuidGeneratorService.generate());
        accomplishment.setProfileId(profileId);

        return accomplishmentRepository.save(accomplishment);
    }
}
