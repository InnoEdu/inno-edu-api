package inno.edu.api.domain.profile.experience.assertions;

import inno.edu.api.domain.common.assertions.EntityExistsAssertion;
import inno.edu.api.domain.profile.experience.exceptions.ExperienceNotFoundException;
import inno.edu.api.domain.profile.experience.repositories.ExperienceRepository;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;
import java.util.function.Function;

@Assertion
public class ExperienceExistsAssertion extends EntityExistsAssertion<ExperienceRepository, ExperienceNotFoundException, Function<UUID, ExperienceNotFoundException>> {
    protected ExperienceExistsAssertion(ExperienceRepository repository) {
        super(repository, ExperienceNotFoundException::new);
    }
}
