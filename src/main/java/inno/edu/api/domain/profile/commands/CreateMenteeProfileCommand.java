package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.commands.dtos.CreateMenteeProfileRequest;
import inno.edu.api.domain.profile.commands.mappers.CreateMenteeProfileRequestToMenteeProfileMapper;
import inno.edu.api.domain.profile.exceptions.MenteeProfileAlreadyCreatedException;
import inno.edu.api.domain.profile.models.MenteeProfile;
import inno.edu.api.domain.profile.repositories.MenteeProfileRepository;
import inno.edu.api.domain.user.assertions.UserExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;

import static java.util.UUID.randomUUID;

@Command
public class CreateMenteeProfileCommand {
    private final CreateMenteeProfileRequestToMenteeProfileMapper createProfileRequestToProfileMapper;
    private final UserExistsAssertion userExistsAssertion;

    private final MenteeProfileRepository profileRepository;

    public CreateMenteeProfileCommand(CreateMenteeProfileRequestToMenteeProfileMapper createProfileRequestToProfileMapper, UserExistsAssertion userExistsAssertion, MenteeProfileRepository profileRepository) {
        this.createProfileRequestToProfileMapper = createProfileRequestToProfileMapper;
        this.userExistsAssertion = userExistsAssertion;
        this.profileRepository = profileRepository;
    }

    public MenteeProfile run(CreateMenteeProfileRequest createMenteeProfileRequest) {
        userExistsAssertion.run(createMenteeProfileRequest.getMenteeId());

        MenteeProfile menteeProfile = createProfileRequestToProfileMapper.createMenteeProfileRequestToMenteeProfile(createMenteeProfileRequest);

        if (profileRepository.existsByMenteeId(createMenteeProfileRequest.getMenteeId())) {
            throw new MenteeProfileAlreadyCreatedException(createMenteeProfileRequest.getMenteeId());
        }

        menteeProfile.setId(randomUUID());
        return profileRepository.save(menteeProfile);
    }
}
