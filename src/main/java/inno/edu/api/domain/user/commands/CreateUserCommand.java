package inno.edu.api.domain.user.commands;

import inno.edu.api.domain.user.exceptions.UserNameAlreadyExistsException;
import inno.edu.api.domain.user.models.User;
import inno.edu.api.domain.user.repositories.UserRepository;
import inno.edu.api.infrastructure.annotations.Command;

import static java.util.UUID.randomUUID;

@Command
public class CreateUserCommand {
    private final UserRepository userRepository;

    public CreateUserCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User run(User user) {
        if (userRepository.existsUserByUserName(user.getUserName())) {
            throw new UserNameAlreadyExistsException(user.getUserName());
        }

        user.setId(randomUUID());
        return userRepository.save(user);
    }
}
