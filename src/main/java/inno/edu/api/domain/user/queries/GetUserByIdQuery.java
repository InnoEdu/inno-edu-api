package inno.edu.api.domain.user.queries;

import inno.edu.api.domain.user.exceptions.UserNotFoundException;
import inno.edu.api.domain.user.models.User;
import inno.edu.api.domain.user.repositories.UserRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Query
public class GetUserByIdQuery {
    private final UserRepository userRepository;

    public GetUserByIdQuery(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User run(UUID id) {
        return ofNullable(userRepository.findOne(id))
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
