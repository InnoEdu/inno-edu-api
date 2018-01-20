package inno.edu.api.domain.profile.experience.commands;

import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.domain.profile.experience.models.dtos.CreateExperienceRequest;
import inno.edu.api.domain.profile.experience.models.dtos.mappers.CreateExperienceRequestMapper;
import inno.edu.api.domain.profile.experience.models.Experience;
import inno.edu.api.domain.profile.experience.repositories.ExperienceRepository;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;

import java.util.UUID;

@Command
public class CreateExperienceCommand {
    private final UUIDGeneratorService uuidGeneratorService;

    private final CreateExperienceRequestMapper createExperienceRequestMapper;
    private final ProfileExistsAssertion profileExistsAssertion;

    private final ExperienceRepository experienceRepository;

    public CreateExperienceCommand(UUIDGeneratorService uuidGeneratorService, CreateExperienceRequestMapper createExperienceRequestMapper, ProfileExistsAssertion profileExistsAssertion, ExperienceRepository experienceRepository) {
        this.uuidGeneratorService = uuidGeneratorService;
        this.createExperienceRequestMapper = createExperienceRequestMapper;
        this.profileExistsAssertion = profileExistsAssertion;
        this.experienceRepository = experienceRepository;
    }

    public Experience run(UUID profileId, CreateExperienceRequest createExperienceRequest) {
        profileExistsAssertion.run(profileId);

        Experience experience = createExperienceRequestMapper.toExperience(createExperienceRequest);
        experience.setId(uuidGeneratorService.generate());
        experience.setProfileId(profileId);

        return experienceRepository.save(experience);
    }
}
