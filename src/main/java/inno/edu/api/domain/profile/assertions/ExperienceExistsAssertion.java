package inno.edu.api.domain.profile.assertions;

import inno.edu.api.domain.profile.exceptions.ExperienceNotFoundException;
import inno.edu.api.domain.profile.repositories.ExperienceRepository;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;

@Assertion
public class ExperienceExistsAssertion {
    private final ExperienceRepository experienceRepository;

    public ExperienceExistsAssertion(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    public void run(UUID id) {
        if (!experienceRepository.exists(id)) {
            throw new ExperienceNotFoundException(id);
        }
    }
}
