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
    private final UpdateMentorProfileStatusCommand updateMentorProfileStatusCommand;

    public DeactivateMentorProfilesCommand(MentorProfileRepository mentorProfileRepository, UserRepository userRepository, UpdateMentorProfileStatusCommand updateMentorProfileStatusCommand) {
        this.mentorProfileRepository = mentorProfileRepository;
        this.userRepository = userRepository;
        this.updateMentorProfileStatusCommand = updateMentorProfileStatusCommand;
    }

    public void run(UUID mentorId) {
        if (!userRepository.existsByIdAndIsMentorIsTrue(mentorId)) {
            throw new UserNotFoundException(mentorId);
        }

        mentorProfileRepository
                .findByMentorId(mentorId)
                .stream()
                .forEach((profile) -> updateMentorProfileStatusCommand.run(profile.getId(), ProfileStatus.INACTIVE));
    }
}
