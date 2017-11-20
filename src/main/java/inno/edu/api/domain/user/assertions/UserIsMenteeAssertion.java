package inno.edu.api.domain.user.assertions;

import inno.edu.api.domain.user.exceptions.UserIsNotMenteeException;
import inno.edu.api.domain.user.repositories.UserRepository;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;

@Assertion
public class UserIsMenteeAssertion {
    private final UserExistsAssertion userExistsAssertion;
    private final UserRepository userRepository;

    public UserIsMenteeAssertion(UserExistsAssertion userExistsAssertion, UserRepository userRepository) {
        this.userExistsAssertion = userExistsAssertion;
        this.userRepository = userRepository;
    }

    public void run(UUID id) {
        userExistsAssertion.run(id);

        if (!userRepository.existsByIdAndIsMentorIsFalse(id)) {
            throw new UserIsNotMenteeException(id);
        }
    }
}
