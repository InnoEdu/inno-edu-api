package inno.edu.api.domain.user.root.assertions;

import inno.edu.api.domain.common.assertions.EntityExistsAssertion;
import inno.edu.api.domain.user.root.exceptions.UserNotFoundException;
import inno.edu.api.domain.user.root.repositories.UserRepository;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;
import java.util.function.Function;

@Assertion
public class UserExistsAssertion extends EntityExistsAssertion<UserRepository, UserNotFoundException, Function<UUID, UserNotFoundException>> {
    protected UserExistsAssertion(UserRepository repository) {
        super(repository, UserNotFoundException::new);
    }
}
