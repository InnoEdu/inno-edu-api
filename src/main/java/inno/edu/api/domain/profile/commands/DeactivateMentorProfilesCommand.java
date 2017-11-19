package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.models.ProfileStatus;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import inno.edu.api.domain.user.assertions.UserIsMentorAssertion;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class DeactivateMentorProfilesCommand {
    private final UserIsMentorAssertion userIsMentorAssertion;

    private final MentorProfileRepository mentorProfileRepository;
    private final UpdateMentorProfileStatusCommand updateMentorProfileStatusCommand;

    public DeactivateMentorProfilesCommand(MentorProfileRepository mentorProfileRepository, UserIsMentorAssertion userIsMentorAssertion, UpdateMentorProfileStatusCommand updateMentorProfileStatusCommand) {
        this.mentorProfileRepository = mentorProfileRepository;
        this.userIsMentorAssertion = userIsMentorAssertion;
        this.updateMentorProfileStatusCommand = updateMentorProfileStatusCommand;
    }

    public void run(UUID mentorId) {
        userIsMentorAssertion.run(mentorId);

        mentorProfileRepository
                .findByMentorId(mentorId)
                .stream()
                .forEach((profile) -> updateMentorProfileStatusCommand.run(profile.getId(), ProfileStatus.INACTIVE));
    }
}
