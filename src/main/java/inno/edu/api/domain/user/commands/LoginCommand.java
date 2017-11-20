package inno.edu.api.domain.user.commands;

import inno.edu.api.domain.user.exceptions.InvalidUsernameOrPasswordException;
import inno.edu.api.domain.user.models.Login;
import inno.edu.api.domain.user.models.User;
import inno.edu.api.domain.user.repositories.UserRepository;
import inno.edu.api.infrastructure.annotations.Command;

import static java.util.Optional.ofNullable;

@Command
public class LoginCommand {
    private final UserRepository userRepository;

    public LoginCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User run(Login credentials) {
        User user = userRepository.findOneByUsernameAndPassword(credentials.getUsername(), credentials.getPassword());
        return ofNullable(user).orElseThrow(InvalidUsernameOrPasswordException::new);
    }
}
