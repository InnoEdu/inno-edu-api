package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.commands.dtos.CreateMentorProfileRequest;
import inno.edu.api.domain.profile.commands.mappers.CreateMentorProfileRequestMapper;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import inno.edu.api.domain.school.assertions.SchoolExistsAssertion;
import inno.edu.api.domain.user.assertions.UserIsMentorAssertion;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;

import static inno.edu.api.domain.profile.models.ProfileStatus.CREATED;

@Command
public class CreateMentorProfileCommand {
    private final UUIDGeneratorService uuidGeneratorService;

    private final CreateMentorProfileRequestMapper createMentorProfileRequestMapper;
    private final UserIsMentorAssertion userIsMentorAssertion;
    private final SchoolExistsAssertion schoolExistsAssertion;

    private final MentorProfileRepository profileRepository;
    private final DeactivateMentorProfilesCommand deactivateMentorProfilesCommand;

    public CreateMentorProfileCommand(UUIDGeneratorService uuidGeneratorService, CreateMentorProfileRequestMapper createMentorProfileRequestMapper, UserIsMentorAssertion userIsMentorAssertion, SchoolExistsAssertion schoolExistsAssertion, MentorProfileRepository profileRepository, DeactivateMentorProfilesCommand deactivateMentorProfilesCommand) {
        this.uuidGeneratorService = uuidGeneratorService;
        this.createMentorProfileRequestMapper = createMentorProfileRequestMapper;
        this.userIsMentorAssertion = userIsMentorAssertion;
        this.schoolExistsAssertion = schoolExistsAssertion;
        this.profileRepository = profileRepository;
        this.deactivateMentorProfilesCommand = deactivateMentorProfilesCommand;
    }

    public MentorProfile run(CreateMentorProfileRequest createMentorProfileRequest) {
        userIsMentorAssertion.run(createMentorProfileRequest.getMentorId());
        schoolExistsAssertion.run(createMentorProfileRequest.getSchoolId());
        deactivateMentorProfilesCommand.run(createMentorProfileRequest.getMentorId());

        MentorProfile profile = createMentorProfileRequestMapper.toMentorProfile(createMentorProfileRequest);

        profile.setId(uuidGeneratorService.generate());
        profile.setStatus(CREATED);

        return profileRepository.save(profile);
    }
}
