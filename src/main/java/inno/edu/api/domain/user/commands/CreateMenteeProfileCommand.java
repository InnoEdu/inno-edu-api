package inno.edu.api.domain.user.commands;

import inno.edu.api.domain.user.models.MenteeProfile;
import inno.edu.api.domain.user.repositories.MenteeProfileRepository;
import inno.edu.api.infrastructure.annotations.Command;

import static java.util.UUID.randomUUID;

@Command
public class CreateMenteeProfileCommand {
    private final MenteeProfileRepository profileRepository;

    public CreateMenteeProfileCommand(MenteeProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public MenteeProfile run(MenteeProfile profile) {
        profile.setId(randomUUID());
        return profileRepository.save(profile);
    }
}
