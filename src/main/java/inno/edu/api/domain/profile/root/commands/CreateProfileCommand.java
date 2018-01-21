package inno.edu.api.domain.profile.root.commands;

import inno.edu.api.domain.profile.root.models.dtos.CreateProfileRequest;
import inno.edu.api.domain.profile.root.models.dtos.mappers.CreateProfileRequestMapper;
import inno.edu.api.domain.profile.root.exceptions.ProfileAlreadyCreatedException;
import inno.edu.api.domain.profile.root.models.Profile;
import inno.edu.api.domain.profile.root.repositories.ProfileRepository;
import inno.edu.api.domain.user.root.assertions.UserExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;

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

        return profileRepository.save(profile);
    }
}
