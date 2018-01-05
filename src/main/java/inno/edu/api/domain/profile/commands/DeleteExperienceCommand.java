package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.assertions.ExperienceExistsAssertion;
import inno.edu.api.domain.profile.repositories.ExperienceRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class DeleteExperienceCommand {
    private final ExperienceExistsAssertion experienceExistsAssertion;
    private final ExperienceRepository experienceRepository;

    public DeleteExperienceCommand(ExperienceExistsAssertion experienceExistsAssertion, ExperienceRepository experienceRepository) {
        this.experienceExistsAssertion = experienceExistsAssertion;
        this.experienceRepository = experienceRepository;
    }

    public void run(UUID id) {
        experienceExistsAssertion.run(id);
        experienceRepository.delete(id);
    }
}
