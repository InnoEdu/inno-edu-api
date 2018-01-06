package inno.edu.api.domain.profile.interest.commands;

import inno.edu.api.domain.profile.interest.assertions.InterestExistsAssertion;
import inno.edu.api.domain.profile.interest.repositories.InterestRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class DeleteInterestCommand {
    private final InterestExistsAssertion experienceExistsAssertion;
    private final InterestRepository experienceRepository;

    public DeleteInterestCommand(InterestExistsAssertion experienceExistsAssertion, InterestRepository experienceRepository) {
        this.experienceExistsAssertion = experienceExistsAssertion;
        this.experienceRepository = experienceRepository;
    }

    public void run(UUID id) {
        experienceExistsAssertion.run(id);
        experienceRepository.delete(id);
    }
}
