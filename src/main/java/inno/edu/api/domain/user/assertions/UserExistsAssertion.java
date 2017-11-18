package inno.edu.api.domain.user.assertions;

import inno.edu.api.domain.user.exceptions.UserNotFoundException;
import inno.edu.api.domain.user.repositories.UserRepository;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;

@Assertion
public class UserExistsAssertion {
    private final UserRepository userRepository;

    public UserExistsAssertion(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void run(UUID id) {
        if (!userRepository.exists(id)) {
                throw new UserNotFoundException(id);
        }
    }
}
