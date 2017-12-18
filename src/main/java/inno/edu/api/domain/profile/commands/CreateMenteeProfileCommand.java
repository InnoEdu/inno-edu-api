package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.commands.dtos.CreateMenteeProfileRequest;
import inno.edu.api.domain.profile.commands.mappers.CreateMenteeProfileRequestMapper;
import inno.edu.api.domain.profile.exceptions.MenteeProfileAlreadyCreatedException;
import inno.edu.api.domain.profile.models.MenteeProfile;
import inno.edu.api.domain.profile.repositories.MenteeProfileRepository;
import inno.edu.api.domain.user.assertions.UserExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;

@Command
public class CreateMenteeProfileCommand {
    private final UUIDGeneratorService uuidGeneratorService;

    private final CreateMenteeProfileRequestMapper createMenteeProfileRequestMapper;
    private final UserExistsAssertion userExistsAssertion;

    private final MenteeProfileRepository profileRepository;

    public CreateMenteeProfileCommand(UUIDGeneratorService uuidGeneratorService, CreateMenteeProfileRequestMapper createMenteeProfileRequestMapper, UserExistsAssertion userExistsAssertion, MenteeProfileRepository profileRepository) {
        this.uuidGeneratorService = uuidGeneratorService;
        this.createMenteeProfileRequestMapper = createMenteeProfileRequestMapper;
        this.userExistsAssertion = userExistsAssertion;
        this.profileRepository = profileRepository;
    }

    public MenteeProfile run(CreateMenteeProfileRequest createMenteeProfileRequest) {
        userExistsAssertion.run(createMenteeProfileRequest.getMenteeId());

        MenteeProfile menteeProfile = createMenteeProfileRequestMapper.toMenteeProfile(createMenteeProfileRequest);

        if (profileRepository.existsByMenteeId(createMenteeProfileRequest.getMenteeId())) {
            throw new MenteeProfileAlreadyCreatedException(createMenteeProfileRequest.getMenteeId());
        }

        menteeProfile.setId(uuidGeneratorService.generate());
        return profileRepository.save(menteeProfile);
    }
}
