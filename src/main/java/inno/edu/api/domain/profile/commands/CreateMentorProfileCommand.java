package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.user.repositories.MentorProfileRepository;
import inno.edu.api.infrastructure.annotations.Command;

import static inno.edu.api.domain.profile.models.ProfileStatus.CREATED;
import static java.util.UUID.randomUUID;

@Command
public class CreateMentorProfileCommand {
    private final MentorProfileRepository profileRepository;
    private final DeactivateMentorProfilesCommand deactivateMentorProfilesCommand;

    public CreateMentorProfileCommand(MentorProfileRepository profileRepository, DeactivateMentorProfilesCommand deactivateMentorProfilesCommand) {
        this.profileRepository = profileRepository;
        this.deactivateMentorProfilesCommand = deactivateMentorProfilesCommand;
    }

    public MentorProfile run(MentorProfile profile) {
        deactivateMentorProfilesCommand.run(profile.getMentorId());

        profile.setId(randomUUID());
        profile.setStatus(CREATED);
        return profileRepository.save(profile);
    }
}
