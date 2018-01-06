package inno.edu.api.domain.profile.accomplishment.commands;

import inno.edu.api.domain.profile.accomplishment.assertions.AccomplishmentExistsAssertion;
import inno.edu.api.domain.profile.accomplishment.repositories.AccomplishmentRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class DeleteAccomplishmentCommand {
    private final AccomplishmentExistsAssertion experienceExistsAssertion;
    private final AccomplishmentRepository experienceRepository;

    public DeleteAccomplishmentCommand(AccomplishmentExistsAssertion experienceExistsAssertion, AccomplishmentRepository experienceRepository) {
        this.experienceExistsAssertion = experienceExistsAssertion;
        this.experienceRepository = experienceRepository;
    }

    public void run(UUID id) {
        experienceExistsAssertion.run(id);
        experienceRepository.delete(id);
    }
}
