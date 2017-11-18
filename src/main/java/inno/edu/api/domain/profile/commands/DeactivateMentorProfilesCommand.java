package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.user.exceptions.UserNotFoundException;
import inno.edu.api.domain.profile.models.ProfileStatus;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import inno.edu.api.domain.user.repositories.UserRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class DeactivateMentorProfilesCommand {
    private final MentorProfileRepository mentorProfileRepository;
    private final UserRepository userRepository;

    public DeactivateMentorProfilesCommand(MentorProfileRepository mentorProfileRepository, UserRepository userRepository) {
        this.mentorProfileRepository = mentorProfileRepository;
        this.userRepository = userRepository;
    }

    public void run(UUID mentorId) {
        if (!userRepository.existsByIdAndIsMentorIsTrue(mentorId)) {
            throw new UserNotFoundException(mentorId);
        }

        mentorProfileRepository
                .findByMentorId(mentorId)
                .stream()
                .forEach((profile) -> {
                    profile.setStatus(ProfileStatus.INACTIVE);
                    mentorProfileRepository.save(profile);
                });
    }
}
