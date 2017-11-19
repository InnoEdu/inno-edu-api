package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import inno.edu.api.domain.school.assertions.SchoolExistsAssertion;
import inno.edu.api.domain.user.assertions.UserExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;

import static inno.edu.api.domain.profile.models.ProfileStatus.CREATED;
import static java.util.UUID.randomUUID;

@Command
public class CreateMentorProfileCommand {
    private final UserExistsAssertion userExistsAssertion;
    private final SchoolExistsAssertion schoolExistsAssertion;

    private final MentorProfileRepository profileRepository;
    private final DeactivateMentorProfilesCommand deactivateMentorProfilesCommand;

    public CreateMentorProfileCommand(UserExistsAssertion userExistsAssertion, SchoolExistsAssertion schoolExistsAssertion, MentorProfileRepository profileRepository, DeactivateMentorProfilesCommand deactivateMentorProfilesCommand) {
        this.userExistsAssertion = userExistsAssertion;
        this.schoolExistsAssertion = schoolExistsAssertion;
        this.profileRepository = profileRepository;
        this.deactivateMentorProfilesCommand = deactivateMentorProfilesCommand;
    }

    public MentorProfile run(MentorProfile profile) {
        userExistsAssertion.run(profile.getMentorId());
        schoolExistsAssertion.run(profile.getSchoolId());

        deactivateMentorProfilesCommand.run(profile.getMentorId());

        profile.setId(randomUUID());
        profile.setStatus(CREATED);
        return profileRepository.save(profile);
    }
}
