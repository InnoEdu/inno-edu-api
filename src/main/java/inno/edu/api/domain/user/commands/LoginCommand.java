package inno.edu.api.domain.user.commands;

import inno.edu.api.domain.user.commands.dtos.LoginRequest;
import inno.edu.api.domain.user.commands.dtos.LoginResponse;
import inno.edu.api.domain.user.exceptions.InvalidUsernameOrPasswordException;
import inno.edu.api.domain.user.models.ApplicationUser;
import inno.edu.api.domain.user.repositories.UserRepository;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.security.jwt.SecurityConstants;
import inno.edu.api.infrastructure.security.service.TokenGeneratorService;

import static java.util.Optional.ofNullable;

@Command
public class LoginCommand {
    private final UserRepository userRepository;
    private final TokenGeneratorService tokenGeneratorService;

    public LoginCommand(UserRepository userRepository, TokenGeneratorService tokenGeneratorService) {
        this.userRepository = userRepository;
        this.tokenGeneratorService = tokenGeneratorService;
    }

    public LoginResponse run(LoginRequest credentials) {
        ApplicationUser user = ofNullable(userRepository.findOneByUsernameAndPassword(credentials.getUsername(), credentials.getPassword()))
                .orElseThrow(InvalidUsernameOrPasswordException::new);

        String token = tokenGeneratorService.generate(user.getUsername());
        return LoginResponse.builder()
                .user(user)
                .token(token)
                .prefixedToken(SecurityConstants.TOKEN_PREFIX + token)
                .build();
    }
}
