package inno.edu.api.domain.profile.interest.assertions;

import inno.edu.api.domain.common.assertions.EntityExistsAssertion;
import inno.edu.api.domain.profile.interest.exceptions.InterestNotFoundException;
import inno.edu.api.domain.profile.interest.repositories.InterestRepository;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;
import java.util.function.Function;

@Assertion
public class InterestExistsAssertion extends EntityExistsAssertion<InterestRepository, InterestNotFoundException, Function<UUID, InterestNotFoundException>> {
    protected InterestExistsAssertion(InterestRepository repository) {
        super(repository, InterestNotFoundException::new);
    }
}
