package inno.edu.api.domain.user.commands;

import inno.edu.api.domain.user.commands.dtos.CreateUserRequest;
import inno.edu.api.domain.user.commands.mappers.CreateUserRequestToUserMapper;
import inno.edu.api.domain.user.exceptions.PasswordMismatchException;
import inno.edu.api.domain.user.exceptions.UsernameAlreadyExistsException;
import inno.edu.api.domain.user.models.User;
import inno.edu.api.domain.user.repositories.UserRepository;
import inno.edu.api.infrastructure.annotations.Command;
import org.apache.commons.lang3.StringUtils;

import static java.util.UUID.randomUUID;

@Command
public class CreateUserCommand {
    private final CreateUserRequestToUserMapper createUserRequestToUserMapper;
    private final UserRepository userRepository;

    public CreateUserCommand(CreateUserRequestToUserMapper createUserRequestToUserMapper, UserRepository userRepository) {
        this.createUserRequestToUserMapper = createUserRequestToUserMapper;
        this.userRepository = userRepository;
    }

    public User run(CreateUserRequest createUserRequest) {
        User user = createUserRequestToUserMapper.createUserRequestToUser(createUserRequest);

        if (!StringUtils.equals(createUserRequest.getPassword(), createUserRequest.getConfirmPassword())) {
            throw new PasswordMismatchException();
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException(user.getUsername());
        }

        user.setId(randomUUID());
        return userRepository.save(user);
    }
}
