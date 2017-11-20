package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import inno.edu.api.domain.school.assertions.SchoolExistsAssertion;
import inno.edu.api.domain.user.assertions.UserIsMentorAssertion;
import inno.edu.api.infrastructure.annotations.Command;

import static inno.edu.api.domain.profile.models.ProfileStatus.CREATED;
import static java.util.UUID.randomUUID;

@Command
public class CreateMentorProfileCommand {
    private final UserIsMentorAssertion userIsMentorAssertion;
    private final SchoolExistsAssertion schoolExistsAssertion;

    private final MentorProfileRepository profileRepository;
    private final DeactivateMentorProfilesCommand deactivateMentorProfilesCommand;

    public CreateMentorProfileCommand(UserIsMentorAssertion userIsMentorAssertion, SchoolExistsAssertion schoolExistsAssertion, MentorProfileRepository profileRepository, DeactivateMentorProfilesCommand deactivateMentorProfilesCommand) {
        this.userIsMentorAssertion = userIsMentorAssertion;
        this.schoolExistsAssertion = schoolExistsAssertion;
        this.profileRepository = profileRepository;
        this.deactivateMentorProfilesCommand = deactivateMentorProfilesCommand;
    }

    public MentorProfile run(MentorProfile profile) {
        userIsMentorAssertion.run(profile.getMentorId());
        schoolExistsAssertion.run(profile.getSchoolId());

        deactivateMentorProfilesCommand.run(profile.getMentorId());

        profile.setId(randomUUID());
        profile.setStatus(CREATED);
        return profileRepository.save(profile);
    }
}
