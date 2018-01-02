package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.commands.dtos.CreateProfileRequest;
import inno.edu.api.domain.profile.commands.mappers.CreateProfileRequestMapper;
import inno.edu.api.domain.profile.exceptions.ProfileAlreadyCreatedException;
import inno.edu.api.domain.profile.models.Profile;
import inno.edu.api.domain.profile.repositories.ProfileRepository;
import inno.edu.api.domain.user.assertions.UserExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;

import static inno.edu.api.domain.profile.models.ProfileStatus.CREATED;

@Command
public class CreateProfileCommand {
    private final UUIDGeneratorService uuidGeneratorService;

    private final CreateProfileRequestMapper createProfileRequestMapper;
    private final UserExistsAssertion userExistsAssertion;

    private final ProfileRepository profileRepository;

    public CreateProfileCommand(UUIDGeneratorService uuidGeneratorService, CreateProfileRequestMapper createProfileRequestMapper, UserExistsAssertion userExistsAssertion, ProfileRepository profileRepository) {
        this.uuidGeneratorService = uuidGeneratorService;
        this.createProfileRequestMapper = createProfileRequestMapper;
        this.userExistsAssertion = userExistsAssertion;
        this.profileRepository = profileRepository;
    }

    public Profile run(CreateProfileRequest createProfileRequest) {
        userExistsAssertion.run(createProfileRequest.getUserId());

        Profile profile = createProfileRequestMapper.toProfile(createProfileRequest);

        if (profileRepository.existsByUserId(createProfileRequest.getUserId())) {
            throw new ProfileAlreadyCreatedException(createProfileRequest.getUserId());
        }

        profile.setId(uuidGeneratorService.generate());
        profile.setStatus(CREATED);

        return profileRepository.save(profile);
    }
}
