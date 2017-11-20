package inno.edu.api.domain.user.commands;

import inno.edu.api.domain.user.commands.dtos.UpdateUserRequest;
import inno.edu.api.domain.user.commands.mappers.UpdateUserRequestToUserMapper;
import inno.edu.api.domain.user.models.User;
import inno.edu.api.domain.user.queries.GetUserByIdQuery;
import inno.edu.api.domain.user.repositories.UserRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class UpdateUserCommand {
    private final UpdateUserRequestToUserMapper updateUserRequestToUserMapper;
    private final UserRepository userRepository;
    private final GetUserByIdQuery getUserByIdQuery;

    public UpdateUserCommand(UpdateUserRequestToUserMapper updateUserRequestToUserMapper, UserRepository userRepository, GetUserByIdQuery getUserByIdQuery) {
        this.updateUserRequestToUserMapper = updateUserRequestToUserMapper;
        this.userRepository = userRepository;
        this.getUserByIdQuery = getUserByIdQuery;
    }

    public User run(UUID id, UpdateUserRequest updateUserRequest) {
        User currentUser = getUserByIdQuery.run(id);
        updateUserRequestToUserMapper.updateUserRequestToUser(updateUserRequest, currentUser);
        return userRepository.save(currentUser);
    }
}
