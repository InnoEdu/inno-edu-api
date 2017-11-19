package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.exceptions.MenteeProfileAlreadyCreatedException;
import inno.edu.api.domain.profile.models.MenteeProfile;
import inno.edu.api.domain.profile.repositories.MenteeProfileRepository;
import inno.edu.api.domain.user.assertions.UserExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;

import static java.util.UUID.randomUUID;

@Command
public class CreateMenteeProfileCommand {
    private final UserExistsAssertion userExistsAssertion;

    private final MenteeProfileRepository profileRepository;

    public CreateMenteeProfileCommand(UserExistsAssertion userExistsAssertion, MenteeProfileRepository profileRepository) {
        this.userExistsAssertion = userExistsAssertion;
        this.profileRepository = profileRepository;
    }

    public MenteeProfile run(MenteeProfile profile) {
        userExistsAssertion.run(profile.getMenteeId());

        if (profileRepository.existsByMenteeId(profile.getMenteeId())) {
            throw new MenteeProfileAlreadyCreatedException(profile.getMenteeId());
        }

        profile.setId(randomUUID());
        return profileRepository.save(profile);
    }
}
