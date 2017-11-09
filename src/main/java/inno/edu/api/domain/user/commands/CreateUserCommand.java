package inno.edu.api.domain.user.commands;

import inno.edu.api.domain.user.models.User;
import inno.edu.api.domain.user.repositories.UserRepository;
import org.springframework.stereotype.Component;

import static java.util.UUID.randomUUID;

@Component
public class CreateUserCommand {
    private final UserRepository userRepository;

    public CreateUserCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User run(User user) {
        user.setId(randomUUID());
        return userRepository.save(user);
    }
}
