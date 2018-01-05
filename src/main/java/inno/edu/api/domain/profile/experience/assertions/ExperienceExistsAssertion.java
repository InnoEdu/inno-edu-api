package inno.edu.api.domain.profile.experience.assertions;

import inno.edu.api.domain.profile.experience.exceptions.ExperienceNotFoundException;
import inno.edu.api.domain.profile.experience.repositories.ExperienceRepository;
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
