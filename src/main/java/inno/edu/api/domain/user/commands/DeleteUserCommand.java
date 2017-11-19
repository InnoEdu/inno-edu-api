package inno.edu.api.domain.user.commands;

import inno.edu.api.domain.user.assertions.UserExistsAssertion;
import inno.edu.api.domain.user.repositories.UserRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class DeleteUserCommand {
    private final UserExistsAssertion userExistsAssertion;
    private final UserRepository userRepository;

    public DeleteUserCommand(UserExistsAssertion userExistsAssertion, UserRepository userRepository) {
        this.userExistsAssertion = userExistsAssertion;
        this.userRepository = userRepository;
    }

    public void run(UUID id) {
        userExistsAssertion.run(id);
        userRepository.delete(id);
    }
}
