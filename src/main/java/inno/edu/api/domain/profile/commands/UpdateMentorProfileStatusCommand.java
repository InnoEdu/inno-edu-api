package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.models.ProfileStatus;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Command
public class UpdateMentorProfileStatusCommand {
    private final MentorProfileRepository mentorProfileRepository;

    public UpdateMentorProfileStatusCommand(MentorProfileRepository mentorProfileRepository) {
        this.mentorProfileRepository = mentorProfileRepository;
    }

    public void run(UUID profileId, ProfileStatus status) {
        MentorProfile profile = ofNullable(mentorProfileRepository.findOne(profileId))
                .orElseThrow(() -> new ProfileNotFoundException(profileId));

        profile.setStatus(status);
        mentorProfileRepository.save(profile);
    }
}
