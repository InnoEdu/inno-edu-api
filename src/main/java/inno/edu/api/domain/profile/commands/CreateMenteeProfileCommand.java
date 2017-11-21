package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.commands.dtos.CreateMenteeProfileRequest;
import inno.edu.api.domain.profile.commands.mappers.CreateMenteeProfileRequestMapper;
import inno.edu.api.domain.profile.exceptions.MenteeProfileAlreadyCreatedException;
import inno.edu.api.domain.profile.models.MenteeProfile;
import inno.edu.api.domain.profile.repositories.MenteeProfileRepository;
import inno.edu.api.domain.user.assertions.UserExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;

import static java.util.UUID.randomUUID;

@Command
public class CreateMenteeProfileCommand {
    private final CreateMenteeProfileRequestMapper createMenteeProfileRequestMapper;
    private final UserExistsAssertion userExistsAssertion;

    private final MenteeProfileRepository profileRepository;

    public CreateMenteeProfileCommand(CreateMenteeProfileRequestMapper createMenteeProfileRequestMapper, UserExistsAssertion userExistsAssertion, MenteeProfileRepository profileRepository) {
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

        menteeProfile.setId(randomUUID());
        return profileRepository.save(menteeProfile);
    }
}
