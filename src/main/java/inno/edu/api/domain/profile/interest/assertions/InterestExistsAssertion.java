package inno.edu.api.domain.profile.interest.assertions;

import inno.edu.api.domain.profile.interest.exceptions.InterestNotFoundException;
import inno.edu.api.domain.profile.interest.repositories.InterestRepository;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;

@Assertion
public class InterestExistsAssertion {
    private final InterestRepository experienceRepository;

    public InterestExistsAssertion(InterestRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    public void run(UUID id) {
        if (!experienceRepository.exists(id)) {
            throw new InterestNotFoundException(id);
        }
    }
}
