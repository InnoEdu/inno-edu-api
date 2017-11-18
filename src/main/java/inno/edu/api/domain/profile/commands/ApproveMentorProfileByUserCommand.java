package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.user.exceptions.UserIsNotMentorException;
import inno.edu.api.domain.user.exceptions.UserNotFoundException;
import inno.edu.api.domain.profile.exceptions.UserProfileNotFoundException;
import inno.edu.api.domain.user.models.MentorProfile;
import inno.edu.api.domain.user.models.ProfileStatus;
import inno.edu.api.domain.user.models.User;
import inno.edu.api.domain.user.repositories.MentorProfileRepository;
import inno.edu.api.domain.user.repositories.UserRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Command
public class ApproveMentorProfileByUserCommand {
    private final UserRepository userRepository;
    private final MentorProfileRepository mentorProfileRepository;

    public ApproveMentorProfileByUserCommand(UserRepository userRepository, MentorProfileRepository mentorProfileRepository) {
        this.userRepository = userRepository;
        this.mentorProfileRepository = mentorProfileRepository;
    }

    public void run(UUID userId) {
        User user = ofNullable(userRepository.findOne(userId))
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (!user.getIsMentor()) {
            throw new UserIsNotMentorException(userId);
        }

        MentorProfile profile = ofNullable(mentorProfileRepository.findOneByMentorIdAndStatus(userId, ProfileStatus.CREATED))
                .orElseThrow(() -> new UserProfileNotFoundException(userId));

        profile.setStatus(ProfileStatus.ACTIVE);

        mentorProfileRepository.save(profile);
    }
}
