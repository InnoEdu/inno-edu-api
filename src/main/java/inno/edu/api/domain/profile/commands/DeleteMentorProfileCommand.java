package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.assertions.MentorProfileExistsAssertion;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class DeleteMentorProfileCommand {
    private final MentorProfileExistsAssertion mentorProfileExistsAssertion;
    private final MentorProfileRepository mentorProfileRepository;

    public DeleteMentorProfileCommand(MentorProfileExistsAssertion mentorProfileExistsAssertion, MentorProfileRepository mentorProfileRepository) {
        this.mentorProfileExistsAssertion = mentorProfileExistsAssertion;
        this.mentorProfileRepository = mentorProfileRepository;
    }

    public void run(UUID id) {
        mentorProfileExistsAssertion.run(id);
        mentorProfileRepository.delete(id);
    }
}
