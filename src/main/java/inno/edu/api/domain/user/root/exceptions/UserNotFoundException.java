package inno.edu.api.domain.user.root.exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID userId) {
        super("Could not find user '" + userId.toString() + "'.");
    }
}