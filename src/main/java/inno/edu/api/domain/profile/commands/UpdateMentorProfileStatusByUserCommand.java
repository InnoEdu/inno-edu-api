package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.exceptions.UserProfileNotFoundException;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.models.ProfileStatus;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import inno.edu.api.domain.user.assertions.UserIsMentorAssertion;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Command
public class UpdateMentorProfileStatusByUserCommand {
    private final UserIsMentorAssertion userIsMentorAssertion;
    private final MentorProfileRepository mentorProfileRepository;

    public UpdateMentorProfileStatusByUserCommand(UserIsMentorAssertion userIsMentorAssertion, MentorProfileRepository mentorProfileRepository) {
        this.userIsMentorAssertion = userIsMentorAssertion;
        this.mentorProfileRepository = mentorProfileRepository;
    }

    public void run(UUID userId, ProfileStatus status) {
        userIsMentorAssertion.run(userId);

        MentorProfile profile = ofNullable(mentorProfileRepository.findOneByMentorIdAndStatus(userId, ProfileStatus.CREATED))
                .orElseThrow(() -> new UserProfileNotFoundException(userId));

        profile.setStatus(status);

        mentorProfileRepository.save(profile);
    }
}
