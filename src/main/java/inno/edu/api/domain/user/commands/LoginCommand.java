package inno.edu.api.domain.user.commands;

import inno.edu.api.domain.user.commands.dtos.LoginRequest;
import inno.edu.api.domain.user.commands.dtos.LoginResponse;
import inno.edu.api.domain.user.exceptions.InvalidUsernameOrPasswordException;
import inno.edu.api.domain.user.models.ApplicationUser;
import inno.edu.api.domain.user.repositories.UserRepository;
import inno.edu.api.infrastructure.annotations.Command;

import static java.util.Optional.ofNullable;

@Command
public class LoginCommand {
    private final UserRepository userRepository;

    public LoginCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponse run(LoginRequest credentials) {
        ApplicationUser applicationUser = ofNullable(userRepository.findOneByUsernameAndPassword(credentials.getUsername(), credentials.getPassword()))
                .orElseThrow(InvalidUsernameOrPasswordException::new);

        return LoginResponse.builder()
                .user(applicationUser)
                .build();
    }
}
