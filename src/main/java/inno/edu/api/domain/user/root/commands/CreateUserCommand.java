package inno.edu.api.domain.user.root.commands;

import inno.edu.api.domain.user.root.models.dtos.CreateUserRequest;
import inno.edu.api.domain.user.root.models.dtos.mappers.CreateUserRequestMapper;
import inno.edu.api.domain.user.root.exceptions.PasswordMismatchException;
import inno.edu.api.domain.user.root.exceptions.UsernameAlreadyExistsException;
import inno.edu.api.domain.user.root.models.ApplicationUser;
import inno.edu.api.domain.user.root.repositories.UserRepository;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import org.apache.commons.lang3.StringUtils;

@Command
public class CreateUserCommand {
    private final UUIDGeneratorService uuidGeneratorService;

    private final CreateUserRequestMapper createUserRequestMapper;
    private final UserRepository userRepository;

    public CreateUserCommand(UUIDGeneratorService uuidGeneratorService, CreateUserRequestMapper createUserRequestMapper, UserRepository userRepository) {
        this.uuidGeneratorService = uuidGeneratorService;
        this.createUserRequestMapper = createUserRequestMapper;
        this.userRepository = userRepository;
    }

    public ApplicationUser run(CreateUserRequest createUserRequest) {
        ApplicationUser applicationUser = createUserRequestMapper.toUser(createUserRequest);

        if (!StringUtils.equals(createUserRequest.getPassword(), createUserRequest.getConfirmPassword())) {
            throw new PasswordMismatchException();
        }

        if (userRepository.existsByUsername(applicationUser.getUsername())) {
            throw new UsernameAlreadyExistsException(applicationUser.getUsername());
        }

        applicationUser.setId(uuidGeneratorService.generate());
        return userRepository.save(applicationUser);
    }
}
