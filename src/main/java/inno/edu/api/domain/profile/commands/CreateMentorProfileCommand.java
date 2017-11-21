package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.commands.dtos.CreateMentorProfileRequest;
import inno.edu.api.domain.profile.commands.mappers.CreateMentorProfileRequestToMentorProfileMapper;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import inno.edu.api.domain.school.assertions.SchoolExistsAssertion;
import inno.edu.api.domain.user.assertions.UserIsMentorAssertion;
import inno.edu.api.infrastructure.annotations.Command;

import static inno.edu.api.domain.profile.models.ProfileStatus.CREATED;
import static java.util.UUID.randomUUID;

@Command
public class CreateMentorProfileCommand {
    private final CreateMentorProfileRequestToMentorProfileMapper createProfileToProfileMapper;
    private final UserIsMentorAssertion userIsMentorAssertion;
    private final SchoolExistsAssertion schoolExistsAssertion;

    private final MentorProfileRepository profileRepository;
    private final DeactivateMentorProfilesCommand deactivateMentorProfilesCommand;

    public CreateMentorProfileCommand(CreateMentorProfileRequestToMentorProfileMapper createProfileToProfileMapper, UserIsMentorAssertion userIsMentorAssertion, SchoolExistsAssertion schoolExistsAssertion, MentorProfileRepository profileRepository, DeactivateMentorProfilesCommand deactivateMentorProfilesCommand) {
        this.createProfileToProfileMapper = createProfileToProfileMapper;
        this.userIsMentorAssertion = userIsMentorAssertion;
        this.schoolExistsAssertion = schoolExistsAssertion;
        this.profileRepository = profileRepository;
        this.deactivateMentorProfilesCommand = deactivateMentorProfilesCommand;
    }

    public MentorProfile run(CreateMentorProfileRequest createMentorProfileRequest) {
        userIsMentorAssertion.run(createMentorProfileRequest.getMentorId());
        schoolExistsAssertion.run(createMentorProfileRequest.getSchoolId());
        deactivateMentorProfilesCommand.run(createMentorProfileRequest.getMentorId());

        MentorProfile profile = createProfileToProfileMapper.createMentorProfileRequestToMentorProfile(createMentorProfileRequest);

        profile.setId(randomUUID());
        profile.setStatus(CREATED);

        return profileRepository.save(profile);
    }
}
