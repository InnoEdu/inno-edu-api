package inno.edu.api.domain.profile.accomplishment.assertions;

import inno.edu.api.domain.common.assertions.EntityExistsAssertion;
import inno.edu.api.domain.profile.accomplishment.exceptions.AccomplishmentNotFoundException;
import inno.edu.api.domain.profile.accomplishment.repositories.AccomplishmentRepository;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;
import java.util.function.Function;

@Assertion
public class AccomplishmentExistsAssertion extends EntityExistsAssertion<AccomplishmentRepository, AccomplishmentNotFoundException, Function<UUID, AccomplishmentNotFoundException>> {
    protected AccomplishmentExistsAssertion(AccomplishmentRepository repository) {
        super(repository, AccomplishmentNotFoundException::new);
    }
}
