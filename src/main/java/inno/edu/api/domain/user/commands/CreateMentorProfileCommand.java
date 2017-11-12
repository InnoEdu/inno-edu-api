package inno.edu.api.domain.user.commands;

import inno.edu.api.domain.user.models.MentorProfile;
import inno.edu.api.domain.user.repositories.MentorProfileRepository;
import inno.edu.api.infrastructure.annotations.Command;

import static java.util.UUID.randomUUID;

@Command
public class CreateMentorProfileCommand {
    private final MentorProfileRepository profileRepository;

    public CreateMentorProfileCommand(MentorProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public MentorProfile run(MentorProfile profile) {
        profile.setId(randomUUID());
        profile.setIsActive(false);
        return profileRepository.save(profile);
    }
}
