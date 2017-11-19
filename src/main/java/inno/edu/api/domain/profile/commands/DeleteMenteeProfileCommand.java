package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.assertions.MenteeProfileExistsAssertion;
import inno.edu.api.domain.profile.repositories.MenteeProfileRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class DeleteMenteeProfileCommand {
    private final MenteeProfileExistsAssertion menteeProfileExistsAssertion;
    private final MenteeProfileRepository menteeProfileRepository;

    public DeleteMenteeProfileCommand(MenteeProfileExistsAssertion menteeProfileExistsAssertion, MenteeProfileRepository menteeProfileRepository) {
        this.menteeProfileExistsAssertion = menteeProfileExistsAssertion;
        this.menteeProfileRepository = menteeProfileRepository;
    }

    public void run(UUID id) {
        menteeProfileExistsAssertion.run(id);
        menteeProfileRepository.delete(id);
    }
}
