package inno.edu.api.domain.user.commands;

import inno.edu.api.domain.user.models.User;
import inno.edu.api.domain.user.queries.GetUserByIdQuery;
import inno.edu.api.domain.user.repositories.UserRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class UpdateUserCommand {
    private final UserRepository userRepository;
    private final GetUserByIdQuery getUserByIdQuery;

    public UpdateUserCommand(UserRepository userRepository, GetUserByIdQuery getUserByIdQuery) {
        this.userRepository = userRepository;
        this.getUserByIdQuery = getUserByIdQuery;
    }

    public User run(UUID id, User user) {
        User currentUser = getUserByIdQuery.run(id);

        currentUser.setId(id);
        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setPhotoUrl(user.getPhotoUrl());

        return userRepository.save(currentUser);
    }
}
