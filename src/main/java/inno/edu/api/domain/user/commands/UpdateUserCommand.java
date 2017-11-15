package inno.edu.api.domain.user.commands;

import inno.edu.api.domain.user.exceptions.UserNotFoundException;
import inno.edu.api.domain.user.models.User;
import inno.edu.api.domain.user.repositories.UserRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Command
public class UpdateUserCommand {
    private final UserRepository userRepository;

    public UpdateUserCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User run(UUID id, User user) {
        User currentUser = ofNullable(userRepository.findOne(id))
                .orElseThrow(() -> new UserNotFoundException(id));

        currentUser.setId(id);
        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setPhotoUrl(user.getPhotoUrl());

        return userRepository.save(currentUser);
    }
}
