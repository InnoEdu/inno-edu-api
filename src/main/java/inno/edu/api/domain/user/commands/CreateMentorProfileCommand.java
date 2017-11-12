package inno.edu.api.domain.user.commands;

import inno.edu.api.domain.user.models.MentorProfile;
import inno.edu.api.domain.user.repositories.MentorProfileRepository;
import inno.edu.api.infrastructure.annotations.Command;

import static inno.edu.api.domain.user.models.ProfileStatus.CREATED;
import static java.util.UUID.randomUUID;

@Command
public class CreateMentorProfileCommand {
    private final MentorProfileRepository profileRepository;

    public CreateMentorProfileCommand(MentorProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public MentorProfile run(MentorProfile profile) {
        profile.setId(randomUUID());
        profile.setStatus(CREATED);
        return profileRepository.save(profile);
    }
}
